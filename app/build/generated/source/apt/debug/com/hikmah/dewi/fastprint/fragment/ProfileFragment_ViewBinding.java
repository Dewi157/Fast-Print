// Generated code from Butter Knife. Do not modify!
package com.hikmah.dewi.fastprint.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.hikmah.dewi.fastprint.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ProfileFragment_ViewBinding implements Unbinder {
  private ProfileFragment target;

  private View view2131296345;

  @UiThread
  public ProfileFragment_ViewBinding(final ProfileFragment target, View source) {
    this.target = target;

    View view;
    target.namaku = Utils.findRequiredViewAsType(source, R.id.nameku, "field 'namaku'", TextView.class);
    target.phoneku = Utils.findRequiredViewAsType(source, R.id.phoneku, "field 'phoneku'", TextView.class);
    target.addressku = Utils.findRequiredViewAsType(source, R.id.addressku, "field 'addressku'", TextView.class);
    target.emailku = Utils.findRequiredViewAsType(source, R.id.emailku, "field 'emailku'", TextView.class);
    target.coordinatorLayout = Utils.findRequiredViewAsType(source, R.id.coordinator_edit, "field 'coordinatorLayout'", CoordinatorLayout.class);
    view = Utils.findRequiredView(source, R.id.edit_profile, "method 'btn_edit'");
    view2131296345 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btn_edit();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    ProfileFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.namaku = null;
    target.phoneku = null;
    target.addressku = null;
    target.emailku = null;
    target.coordinatorLayout = null;

    view2131296345.setOnClickListener(null);
    view2131296345 = null;
  }
}
