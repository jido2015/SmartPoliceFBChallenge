package yct.smartpolicefbchallenge;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import yct.smartpolicefbchallenge.object_report.Date_setter_getter;
import yct.smartpolicefbchallenge.time_and_date_classes.ChoiceDialogFragment;
import yct.smartpolicefbchallenge.time_and_date_classes.DatePickerFragment;
import yct.smartpolicefbchallenge.time_and_date_classes.TimePickerFragment;

/**
 * Created by mac on 10/6/17.
 */

public class ReportDetailsFragment extends Fragment {

    private static final int REQUEST_DATE = 0;

    public static final int REQUEST_TIME = 0xfe;
    public static final int REQUEST_CHOICE = 0xfd;

    private static final int GALLERY_REQUEST = 1;

    public String crime_selection;
    public String mUserId;

    public Button sendButton;
    public ImageView imageView;
    public Button datetimeButton;
    public EditText edit_location;

    //private Button btn_cancel;

    public ImageButton captureButton;

    private EditText reportDetails;

    private Date_setter_getter mDate;


    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    public Spinner crimeType;
    private ProgressDialog mProgressDialog;

    private Uri imageUri = null;
    DatabaseReference mDatabase;
    private StorageReference mStorage;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDate = new Date_setter_getter();


}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_report, container, false);


        crime_selection ="a";



        crimeType = v.findViewById(R.id.crimeType);
        imageView = v.findViewById(R.id.imageView);
        datetimeButton = v.findViewById(R.id.datetimeButton);
        reportDetails = v.findViewById(R.id.reportDetails);
        edit_location = v.findViewById(R.id.edit_location);
        sendButton = v.findViewById(R.id.sendButton);
        captureButton = v.findViewById(R.id.captureButton);


        // Initializing a String Array
        String[] plants = new String[]{
                "Black alder",
                "Speckled alder",
                "Striped alder"
        };


        // Initializing an ArrayAdapter
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(
                getActivity(), android.R.layout.simple_spinner_item, plants
        );

        /*
            setDropDownViewResource(int resource)
                Sets the layout resource to create the drop down views.

                Parameters : resource
                    the layout resource defining the drop down views
         */
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        /*
            setAdapter(SpinnerAdapter adapter)
                Sets the Adapter used to provide the data which backs this Spinner.
         */
        crimeType.setAdapter(spinnerArrayAdapter);

        crimeType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                crime_selection= parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Another interface callback
            }
        });




        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser == null) {
            // Not logged in, launch the Log In activity

            System.exit(0);

        }
        else

        {











            // Initialize Firebase Auth and Database Reference
            mStorage = FirebaseStorage.getInstance().getReference();
            mDatabase = FirebaseDatabase.getInstance().getReference().child("Reports");

            mUserId = mFirebaseUser.getUid();


            sendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startPosting();
                }
                private void startPosting(){

                    mProgressDialog.setMessage("Sending Report..." );
                    mProgressDialog.show();




                    final String crime_date = datetimeButton.getText().toString().trim();
                    final String txt_reportDetails = reportDetails.getText().toString().trim();
                    final String crime_location = edit_location.getText().toString().trim();




                    if (!(TextUtils.isEmpty(crime_selection)) && !(TextUtils.isEmpty(crime_date)) &&
                            !(TextUtils.isEmpty(crime_location)) && !(TextUtils.isEmpty(txt_reportDetails))){

                        StorageReference filepath= mStorage.child("images").child(imageUri.getLastPathSegment());
                        filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {



                                Uri downloadUrl = taskSnapshot.getDownloadUrl();


                                DatabaseReference newPost = mDatabase.push();
                                newPost.child("crime_type").setValue(crime_selection);
                                newPost.child("crime_details").setValue(txt_reportDetails);
                                newPost.child("crime_date").setValue(crime_date);
                                newPost.child("crime_image").setValue(downloadUrl.toString());
                                newPost.child("crime_location").setValue(crime_location);

                                mProgressDialog.dismiss();

                                Toast.makeText(getActivity(), "Uploaded successfully", Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(getContext(), MainActivity.class));
                            }


                        });


                    }

                    else{
                        Toast.makeText(getActivity(), "Not uploading", Toast.LENGTH_SHORT).show();
                    }
                }


            });
        }



            mProgressDialog = new ProgressDialog(getActivity());


        datetimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDateTimeDialog();
            }
        });



        select_photo();
        return v;



    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK)
            return;


        if (requestCode == GALLERY_REQUEST){
         imageUri = data.getData();

           Glide.with(getActivity()).load(imageUri).centerCrop().into(imageView);

        }

        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            combineDate(date);


            updateDate();
        }
        if (requestCode == REQUEST_TIME) {
            Date date = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            combineTime(date);
            updateDate();
        }

        if (requestCode == REQUEST_CHOICE) {
            int choice =
                    data.getIntExtra(ChoiceDialogFragment.EXTRA_CHOICE, 0);
            if (choice == 0) {
                Log.d("choice date ", "requested choice return nothing");
                return;
            }
            if (choice == ChoiceDialogFragment.CHOICE_TIME) editTimeDialog();
            else if (choice == ChoiceDialogFragment.CHOICE_DATE) editDateDialog();
        }
    }

    public void editDateDialog(){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        DatePickerFragment dialog = DatePickerFragment.newInstance(mDate.getDate());
        dialog.setTargetFragment(ReportDetailsFragment.this, REQUEST_DATE);
        dialog.show(fm, null);
    }
    public void editTimeDialog(){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        TimePickerFragment dialog = TimePickerFragment.newInstance(mDate.getDate());
        dialog.setTargetFragment(ReportDetailsFragment.this, REQUEST_TIME);
        dialog.show(fm, null);
    }

    public void editDateTimeDialog(){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        ChoiceDialogFragment dialogFragment = new ChoiceDialogFragment();
      dialogFragment.setTargetFragment(ReportDetailsFragment.this, REQUEST_CHOICE);
        dialogFragment.show(fm, null);
    }

    public void combineTime(Date time){
        Calendar cal = Calendar.getInstance();
        cal.setTime(mDate.getDate());
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(time);
        int hours = cal.get(Calendar.HOUR_OF_DAY);
        int mins = cal.get(Calendar.MINUTE);

        Date finalD =  new GregorianCalendar(year,month, day, hours, mins).getTime();
        mDate.setDate(finalD);
    }
    public void combineDate(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(mDate.getDate());
        int hours = cal.get(Calendar.HOUR_OF_DAY);
        int mins = cal.get(Calendar.MINUTE);


        Date finalD =  new GregorianCalendar(year,month, day, hours, mins).getTime();
        mDate.setDate(finalD);
    }

    private void updateDate() {

        String dateFormat = "MMM. d, yyyy.  h:m: a";
        String dateString = android.text.format.DateFormat.format(dateFormat,
                mDate.getDate()).toString();
        datetimeButton.setText(dateString);
    }




   private void select_photo(){

       captureButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
               galleryIntent.setType("image/*");
               startActivityForResult(galleryIntent, GALLERY_REQUEST);


           }
       });

   }


}
