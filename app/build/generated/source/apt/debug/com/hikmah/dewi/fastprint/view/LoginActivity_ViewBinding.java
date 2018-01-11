// Generated code from Butter Knife. Do not modify!
package com.hikmah.dewi.fastprint.view;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.hikmah.dewi.fastprint.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginActivity_ViewBinding implements Unbinder {
  private LoginActivity target;

  private View view2131296485;

  private View view2131296300;

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_ViewBinding(final LoginActivity target, View source) {
    this.target = target;

    View view;
    target.cardView = Utils.findRequiredViewAsType(source, R.id.card, "field 'cardView'", CardView.class);
    target.coordinatorLayout = Utils.findRequiredViewAsType(source, R.id.coordinator_login, "field 'coordinatorLayout'", CoordinatorLayout.class);
    target.phone = Utils.findRequiredViewAsType(source, R.id.phone, "field 'phone'", EditText.class);
    target.password = Utils.findRequiredViewAsType(source, R.id.password, "field 'password'", EditText.class);
    view = Utils.findRequiredView(source, R.id.tanya, "method 'tanya'");
    view2131296485 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.tanya();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_login, "method 'btn_login'");
    view2131296300 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btn_login();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.cardView = null;
    target.coordinatorLayout = null;
    target.phone = null;
    target.password = null;

    view2131296485.setOnClickListener(null);
    view2131296485 = null;
    view2131296300.setOnClickListener(null);
    view2131296300 = null;
  }
}
