package com.commin.pro.exerciseproject.page.photo;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.commin.pro.exerciseproject.ApplicationProperty;
import com.commin.pro.exerciseproject.R;
import com.commin.pro.exerciseproject.dao.Dao2Excercise;
import com.commin.pro.exerciseproject.model.Model2Excercise;
import com.commin.pro.exerciseproject.page.photo_edit.Page2PhotoEdit;
import com.commin.pro.exerciseproject.util.UtilDialog;
import com.commin.pro.exerciseproject.util.UtilImage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/*
Model에 저장되어있는 사진이나 Camera를 통해 받아온 사진 데이터를
확인하고 edit Activity로 보내서 편집후 다시 받아오는 클래스입니다.
 */
public class Page2Photo extends AppCompatActivity {
    private Button btn_photo, btn_edit, btn_save;
    private ImageView iv_photo_user_image;
    private Spinner sp_event_date_selector;
    private ArrayList<String> items;
    private SimpleDateFormat df = new SimpleDateFormat("MM월 dd일");
    private String user_photo_path = "";
    private Bitmap user_photo = null;
    private Date selected_date = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_photo_layout);
        createGUI();
        getPermission();
    }

    private void getPermission() {
        if (ContextCompat.checkSelfPermission(Page2Photo.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(Page2Photo.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(Page2Photo.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, ApplicationProperty.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        }
    }


    private void createGUI() {
        init_elements();
        init_spinner();
        init_click_handler();

    }

    private void init_elements() {
        btn_photo = (Button) findViewById(R.id.btn_photo);
        btn_edit = (Button) findViewById(R.id.btn_edit);
        btn_save = (Button) findViewById(R.id.btn_save);
        iv_photo_user_image = (ImageView) findViewById(R.id.iv_photo_user_image);
        iv_photo_user_image.setScaleType(ImageView.ScaleType.FIT_XY);
        sp_event_date_selector = (Spinner) findViewById(R.id.sp_event_date_selector);
    }

    private void init_spinner() {
        HashMap<Date, Model2Excercise> map = Dao2Excercise.getHashMap();
        items = new ArrayList<String>();

        items.add(getResources().getString(R.string.sp_title));
        for (Date date : map.keySet()) {
            items.add(df.format(date));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Page2Photo.this, android.R.layout.simple_spinner_dropdown_item, items);
        sp_event_date_selector.setAdapter(adapter);

    }

    private void init_click_handler() {
        btn_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sp_event_date_selector.getSelectedItemPosition() == 0) {
                    UtilDialog.showToast(Page2Photo.this, "먼저 운동한 날을 선택 하세요");
                    return;
                }
                playCameraOrGallery(view);
            }
        });
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sp_event_date_selector.getSelectedItemPosition() == 0) {
                    UtilDialog.showToast(Page2Photo.this, "먼저 운동한 날을 선택 하세요");
                    return;
                }
                if (user_photo_path == null) {
                    UtilDialog.showToast(Page2Photo.this, "사진을 찍으세요.");
                    return;
                }

                Model2Excercise model = new Model2Excercise();
                model.setUser_photo_path(user_photo_path);
                model.setDate(selected_date);
                Intent intent = new Intent(Page2Photo.this, Page2PhotoEdit.class);
                intent.putExtra("Model2Excercise", model);
                startActivityForResult(intent, ApplicationProperty.REQUEST_CODE_FOR_PHOTO_EDIT);

            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sp_event_date_selector.getSelectedItemPosition() == 0 && selected_date == null) {
                    UtilDialog.showToast(Page2Photo.this, "먼저 운동한 날을 선택 하세요");
                    return;
                }
                if (user_photo_path == null) {
                    UtilDialog.showToast(Page2Photo.this, "사진을 찍으세요.");
                    return;
                }
                Model2Excercise model = Dao2Excercise.getHashMap().get(selected_date);
                model.setUser_photo_path(user_photo_path);
                Dao2Excercise.updateModel(selected_date, model);
                UtilDialog.showToast(Page2Photo.this, "업로드 완료");
                finish();
            }
        });
        sp_event_date_selector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View view, int position, long l) {
                if (items.get(position).equals(getResources().getString(R.string.sp_title))) {
                    selected_date = null;
                    return;
                }
                Date date = Dao2Excercise.queryDate(items.get(position), df);
                if (date == null) {
                    return;
                }
                changeImage(date);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    private void changeImage(Date date) {
        Model2Excercise model = Dao2Excercise.getHashMap().get(date);
        selected_date = date;
        String imsi = model.getUser_photo_path();
        if (imsi == null) {
            UtilDialog.showToast(Page2Photo.this, "선택한날은 사진이 없습니다.");
            user_photo_path = null;
            iv_photo_user_image.setImageDrawable(null);
            return;
        }
        setImageSource(imsi);

        if (user_photo != null) {
            UtilDialog.showToast(Page2Photo.this, "사진을 불러왔습니다.");
            iv_photo_user_image.setImageBitmap(user_photo);
        } else {
            UtilDialog.openError(Page2Photo.this, getResources().getString(R.string.load_image_file_fail), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
        }

    }

    private void setImageSource(String image_path) {
        user_photo_path = image_path;
        user_photo = UtilImage.getBitmap(user_photo_path);
    }


    private void playCameraOrGallery(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.btn_photo:
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString());
                try {
                    intent.putExtra("return-data", true);
                    startActivityForResult(Intent.createChooser(intent,
                            "Complete action using"), ApplicationProperty.PICK_FROM_CAMERA);
                } catch (ActivityNotFoundException e) {
                }

                break;

            //갤러리 부분 추가시 이곳에 추가하시면됩니다.
            //case R.id.btn_gallery:
        }

        if (intent == null) {
            return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == ApplicationProperty.RESULT_CODE_FOR_PHOTO_EDIT) {
            UtilDialog.showToast(Page2Photo.this, "edit complete");
            Model2Excercise model = (Model2Excercise) intent.getSerializableExtra("Model2Excercise");
            model.getDate();
            setImageSource(model.getUser_photo_path());

            if (user_photo != null) {
                UtilDialog.showToast(Page2Photo.this, "사진을 불러왔습니다.");
                iv_photo_user_image.setImageBitmap(user_photo);
            } else {
                UtilDialog.openError(Page2Photo.this, getResources().getString(R.string.load_image_file_fail), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
            }
            return;
        }


        if (resultCode == RESULT_OK) {

            Uri uri_datas = intent.getData();

            if (uri_datas != null) {
                user_photo_path = getPath(uri_datas);

                int degree = UtilImage.getExifOrientation(user_photo_path);
                user_photo = UtilImage.getBitmap(user_photo_path, 0, 0, false);
                user_photo = UtilImage.getRotatedBitmap(user_photo, degree);

                if (user_photo != null) {
                    user_photo_path = UtilImage.getImageCode(Page2Photo.this, user_photo);
                    iv_photo_user_image.setImageBitmap(user_photo);
                } else {
                    UtilDialog.openError(Page2Photo.this, getResources().getString(R.string.load_image_file_fail), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                }
            }
        } else if (resultCode == RESULT_CANCELED) {
            return;
        }
    }

    private String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case ApplicationProperty.MY_PERMISSIONS_REQUEST_CAMERA: {//CAMERA
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {

                }
                return;
            }

            case ApplicationProperty.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {

                }
                return;
            }
            case ApplicationProperty.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {

                }
                return;
            }


        }
    }

}
