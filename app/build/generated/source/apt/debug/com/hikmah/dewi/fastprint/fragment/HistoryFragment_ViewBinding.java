// Generated code from Butter Knife. Do not modify!
package com.hikmah.dewi.fastprint.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.hikmah.dewi.fastprint.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class HistoryFragment_ViewBinding implements Unbinder {
  private HistoryFragment target;

  @UiThread
  public HistoryFragment_ViewBinding(HistoryFragment target, View source) {
    this.target = target;

    target.mRecyclerView = Utils.findRequiredViewAsType(source, R.id.history_list, "field 'mRecyclerView'", RecyclerView.class);
    target.coordinatorLayout = Utils.findRequiredViewAsType(source, R.id.coodinator_histoty, "field 'coordinatorLayout'", CoordinatorLayout.class);
    target.swipe = Utils.findRequiredViewAsType(source, R.id.swipe_histor, "field 'swipe'", SwipeRefreshLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    HistoryFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mRecyclerView = null;
    target.coordinatorLayout = null;
    target.swipe = null;
  }
}
