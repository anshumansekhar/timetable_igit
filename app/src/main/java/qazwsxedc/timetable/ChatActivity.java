package qazwsxedc.timetable;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {

    static final int RC_PHOTO_PICKER = 1;

    private FloatingActionButton sendBtn;
    private EditText messageTxt;
    private RecyclerView messagesList;
    private MessageAdapter adapter;
    private ImageButton imageBtn;
    private ProgressBar progressBar;
    private ProgressBar pb;
    Uri photo;


    private FirebaseApp app;
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private FirebaseStorage storage;
    FirebaseUser user;

    private DatabaseReference databaseRef;
    private StorageReference storageRef;
    private boolean shouldAskPermission(){

        return(Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP_MR1);

    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ActionBar ab=getSupportActionBar();
        ab.setTitle("CSE United");
        user=FirebaseAuth.getInstance().getCurrentUser();
        if(user==null)
        {
            startActivity(new Intent(ChatActivity.this,SignInActivity.class));
        }
        sendBtn = (FloatingActionButton) findViewById(R.id.Send);
        messageTxt = (EditText) findViewById(R.id.messageEditText);
        messagesList = (RecyclerView) findViewById(R.id.messageRecyclerView);
        imageBtn = (ImageButton) findViewById(R.id.send_image);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        messageTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    sendBtn.setEnabled(true);
                } else {
                    sendBtn.setEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    sendBtn.setEnabled(true);
                } else {
                    sendBtn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if (type.startsWith("image/")) {
                handleSendImage(intent);
            }
        } else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
            if (type.startsWith("image/")) {
                handleSendMultipleImages(intent);
            }
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        messagesList.setHasFixedSize(false);
        messagesList.setLayoutManager(layoutManager);
        imageBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int i=ContextCompat.checkSelfPermission(ChatActivity.this,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE);
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
               if(i!=0 )
                   startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
                String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE","android.permission.READ_EXTERNAL_STORAGE"};
                int permsRequestCode = 200;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(perms, permsRequestCode);
                }


            }
        });
        adapter = new MessageAdapter(this);
        messagesList.setAdapter(adapter);
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            public void onItemRangeInserted(int positionStart, int itemCount) {
                messagesList.smoothScrollToPosition(adapter.getItemCount());
            }
        });
        app = FirebaseApp.getInstance();
        database = FirebaseDatabase.getInstance(app);
        auth = FirebaseAuth.getInstance(app);
        storage = FirebaseStorage.getInstance(app);
        if(user.getPhotoUrl()!=null)
        {
            photo=user.getPhotoUrl();
        }
        else photo=Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                +"://"+getResources().getResourcePackageName(R.drawable.cast_album_art_placeholder)
                +"/"+ getResources().getResourceTypeName(R.drawable.cast_album_art_placeholder)
                +"/"+ getResources().getResourceEntryName(R.drawable.cast_album_art_placeholder));
        databaseRef = database.getReference("messages");
               sendBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Message chat = new Message( messageTxt.getText().toString(),user.getDisplayName(),photo.toString());
                databaseRef.push().setValue(chat);
                messageTxt.setText("");
            }
        });
        databaseRef.addChildEventListener(new ChildEventListener() {
            public void onChildAdded(DataSnapshot snapshot, String s) {
                Message chat = snapshot.getValue(Message.class);
                progressBar.setVisibility(View.INVISIBLE);
                adapter.addMessage(chat);
            }

            public void onChildChanged(DataSnapshot dataSnapshot, String s) { }
            public void onChildRemoved(DataSnapshot dataSnapshot) { }
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }
            public void onCancelled(DatabaseError databaseError) { }
        });

            }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            storageRef = storage.getReference("CSE_Group_Chat");
            final StorageReference photoRef = storageRef.child(selectedImageUri.getLastPathSegment());
            final ProgressDialog progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("Uploading");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setIndeterminate(true);
            UploadTask task=photoRef.putFile(selectedImageUri);
            progressDialog.setMax(100);
            progressDialog.show();
            task.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.setProgress((int)(taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount())*100);
                    progressDialog.onContentChanged();
                }
            });
            task.addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressDialog.hide();
                                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                Message chat = new Message( downloadUrl.toString(),user.getDisplayName(),photo.toString());
                                databaseRef.push().setValue(chat);
                                messageTxt.setText("");
                            }
                        });
            }
        }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults){

        switch(permsRequestCode){

            case 200:

                boolean writeAccepted = grantResults[0]== PackageManager.PERMISSION_GRANTED;
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);

                break;

        }

    }
    void handleSendImage(Intent intent) {
        Uri imageUri = (Uri)intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (imageUri != null) {
            int i=ContextCompat.checkSelfPermission(ChatActivity.this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE);
            if(i!=0) {
                FirebaseStorage storage = FirebaseStorage.getInstance();
                storageRef = storage.getReference("CSE_Group_Chat");
                final StorageReference photoRef = storageRef.child(imageUri.getLastPathSegment());
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Uploading");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setIndeterminate(true);
                UploadTask task = photoRef.putFile(imageUri);
                progressDialog.setMax(100);
                progressDialog.show();
                task.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.setProgress((int) (taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount()) * 100);
                        progressDialog.onContentChanged();
                    }
                });
                task.addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.hide();
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Message chat = new Message(downloadUrl.toString(), user.getDisplayName(), photo.toString());
                        databaseRef.push().setValue(chat);
                        messageTxt.setText("");
                    }
                });
            }
            String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE","android.permission.READ_EXTERNAL_STORAGE"};
            int permsRequestCode = 200;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(perms, permsRequestCode);
            }
        }
    }
    void handleSendMultipleImages(Intent intent) {
        ArrayList<Uri> imageUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
        int i=ContextCompat.checkSelfPermission(ChatActivity.this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE);
        if(i!=0) {
            if (imageUris != null) {
                for (Uri uri : imageUris) {
                    storageRef = storage.getReference("CSE_Group_Chat");
                    final StorageReference photoRef = storageRef.child(uri.getLastPathSegment());
                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("Uploading");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progressDialog.setIndeterminate(true);
                    UploadTask task = photoRef.putFile(uri);
                    progressDialog.setMax(100);
                    progressDialog.show();
                    task.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.setProgress((int) (taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount()) * 100);
                            progressDialog.onContentChanged();
                        }
                    });
                    task.addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.hide();
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            Message chat = new Message(downloadUrl.toString(), user.getDisplayName(), photo.toString());
                            databaseRef.push().setValue(chat);
                            messageTxt.setText("");
                        }
                    });
                }
            }
        }

                String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE","android.permission.READ_EXTERNAL_STORAGE"};
                int permsRequestCode = 200;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(perms, permsRequestCode);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.users)
        {
            startActivity(new Intent(ChatActivity.this,Users.class));

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
