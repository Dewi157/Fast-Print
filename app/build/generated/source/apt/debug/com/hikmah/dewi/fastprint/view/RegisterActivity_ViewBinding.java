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

public class RegisterActivity_ViewBinding implements Unbinder {
  private RegisterActivity target;

  private View view2131296486;

  private View view2131296435;

  @UiThread
  public RegisterActivity_ViewBinding(RegisterActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public RegisterActivity_ViewBinding(final RegisterActivity target, View source) {
    this.target = target;

    View view;
    target.cardView = Utils.findRequiredViewAsType(source, R.id.card_register, "field 'cardView'", CardView.class);
    target.p_register = Utils.findRequiredViewAsType(source, R.id.password_register, "field 'p_register'", EditText.class);
    target.n_register = Utils.findRequiredViewAsType(source, R.id.nama, "field 'n_register'", EditText.class);
    target.ph_register = Utils.findRequiredViewAsType(source, R.id.phone_register, "field 'ph_register'", EditText.class);
    target.coordinatorLayout = Utils.findRequiredViewAsType(source, R.id.coordinator_register, "field 'coordinatorLayout'", CoordinatorLayout.class);
    view = Utils.findRequiredView(source, R.id.tanya_register, "method 'tayan_register'");
    view2131296486 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.tayan_register();
      }
    });
    view = Utils.findRequiredView(source, R.id.register, "method 'btn_register'");
    view2131296435 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btn_register();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    RegisterActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.cardView = null;
    target.p_register = null;
    target.n_register = null;
    target.ph_register = null;
    target.coordinatorLayout = null;

    view2131296486.setOnClickListener(null);
    view2131296486 = null;
    view2131296435.setOnClickListener(null);
    view2131296435 = null;
  }
}
