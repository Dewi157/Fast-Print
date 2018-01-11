// Generated code from Butter Knife. Do not modify!
package com.hikmah.dewi.fastprint.view;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.hikmah.dewi.fastprint.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class OrderActivity_ViewBinding implements Unbinder {
  private OrderActivity target;

  private View view2131296304;

  private View view2131296299;

  private View view2131296302;

  @UiThread
  public OrderActivity_ViewBinding(OrderActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public OrderActivity_ViewBinding(final OrderActivity target, View source) {
    this.target = target;

    View view;
    target.coordinatorLayout = Utils.findRequiredViewAsType(source, R.id.coordinator_order, "field 'coordinatorLayout'", CoordinatorLayout.class);
    target.imageView = Utils.findRequiredViewAsType(source, R.id.imagepdf, "field 'imageView'", ImageView.class);
    target.nama_rental = Utils.findRequiredViewAsType(source, R.id.nama_rental, "field 'nama_rental'", TextView.class);
    target.info_order = Utils.findRequiredViewAsType(source, R.id.info_order, "field 'info_order'", TextView.class);
    target.jilid = Utils.findRequiredViewAsType(source, R.id.spjilid, "field 'jilid'", Spinner.class);
    target.cover = Utils.findRequiredViewAsType(source, R.id.spjeniscover, "field 'cover'", Spinner.class);
    target.warna = Utils.findRequiredViewAsType(source, R.id.spwarna, "field 'warna'", Spinner.class);
    view = Utils.findRequiredView(source, R.id.btn_view, "method 'view'");
    view2131296304 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.view();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_attach, "method 'attach'");
    view2131296299 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.attach();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_order, "method 'order'");
    view2131296302 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.order();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    OrderActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.coordinatorLayout = null;
    target.imageView = null;
    target.nama_rental = null;
    target.info_order = null;
    target.jilid = null;
    target.cover = null;
    target.warna = null;

    view2131296304.setOnClickListener(null);
    view2131296304 = null;
    view2131296299.setOnClickListener(null);
    view2131296299 = null;
    view2131296302.setOnClickListener(null);
    view2131296302 = null;
  }
}
