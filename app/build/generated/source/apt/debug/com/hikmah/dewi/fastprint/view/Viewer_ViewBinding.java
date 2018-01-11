// Generated code from Butter Knife. Do not modify!
package com.hikmah.dewi.fastprint.view;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.hikmah.dewi.fastprint.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class Viewer_ViewBinding implements Unbinder {
  private Viewer target;

  @UiThread
  public Viewer_ViewBinding(Viewer target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public Viewer_ViewBinding(Viewer target, View source) {
    this.target = target;

    target.coordinatorLayout = Utils.findRequiredViewAsType(source, R.id.coordinator_view, "field 'coordinatorLayout'", CoordinatorLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    Viewer target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.coordinatorLayout = null;
  }
}
