// Generated by view binder compiler. Do not edit!
package com.example.hanateyes.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.example.hanateyes.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityTransferPopupBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final EditText account;

  @NonNull
  public final Button btn1;

  @NonNull
  public final Button btn2;

  @NonNull
  public final Button btnTransferOn;

  @NonNull
  public final EditText price;

  private ActivityTransferPopupBinding(@NonNull LinearLayout rootView, @NonNull EditText account,
      @NonNull Button btn1, @NonNull Button btn2, @NonNull Button btnTransferOn,
      @NonNull EditText price) {
    this.rootView = rootView;
    this.account = account;
    this.btn1 = btn1;
    this.btn2 = btn2;
    this.btnTransferOn = btnTransferOn;
    this.price = price;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityTransferPopupBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityTransferPopupBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_transfer_popup, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityTransferPopupBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.account;
      EditText account = rootView.findViewById(id);
      if (account == null) {
        break missingId;
      }

      id = R.id.btn1;
      Button btn1 = rootView.findViewById(id);
      if (btn1 == null) {
        break missingId;
      }

      id = R.id.btn2;
      Button btn2 = rootView.findViewById(id);
      if (btn2 == null) {
        break missingId;
      }

      id = R.id.btn_transfer_on;
      Button btnTransferOn = rootView.findViewById(id);
      if (btnTransferOn == null) {
        break missingId;
      }

      id = R.id.price;
      EditText price = rootView.findViewById(id);
      if (price == null) {
        break missingId;
      }

      return new ActivityTransferPopupBinding((LinearLayout) rootView, account, btn1, btn2,
          btnTransferOn, price);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
