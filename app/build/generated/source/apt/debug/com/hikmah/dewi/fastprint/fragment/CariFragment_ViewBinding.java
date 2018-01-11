// Generated code from Butter Knife. Do not modify!
package com.hikmah.dewi.fastprint.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.hikmah.dewi.fastprint.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CariFragment_ViewBinding implements Unbinder {
  private CariFragment target;

  @UiThread
  public CariFragment_ViewBinding(CariFragment target, View source) {
    this.target = target;

    target.coordinatorLayout = Utils.findRequiredViewAsType(source, R.id.coodinator_cari, "field 'coordinatorLayout'", CoordinatorLayout.class);
    target.swipe = Utils.findRequiredViewAsType(source, R.id.swipe, "field 'swipe'", SwipeRefreshLayout.class);
    target.list = Utils.findRequiredViewAsType(source, R.id.listView, "field 'list'", ListView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CariFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.coordinatorLayout = null;
    target.swipe = null;
    target.list = null;
  }
}
