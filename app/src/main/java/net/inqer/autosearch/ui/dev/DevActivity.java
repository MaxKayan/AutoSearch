package net.inqer.autosearch.ui.dev;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import net.inqer.autosearch.databinding.ActivityDevBinding;

public class DevActivity extends AppCompatActivity {
    private static final String TAG = "DevActivity";

    private ActivityDevBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDevBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActivityResultLauncher<Void> cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), new ActivityResultCallback<Bitmap>() {
            @Override
            public void onActivityResult(Bitmap result) {
                Log.i(TAG, "onActivityResult: cameraLauncher: " + result.toString());

                binding.imageView.setImageBitmap(result);
            }
        });

        binding.button1.setOnClickListener(v -> {
            Snackbar.make(v, "Pressed", Snackbar.LENGTH_LONG).show();
            cameraLauncher.launch(null);
        });
        binding.button1.setOnLongClickListener(v -> {
            return true;
        });
    }
}
