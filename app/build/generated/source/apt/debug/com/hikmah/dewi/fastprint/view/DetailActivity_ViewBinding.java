// Generated code from Butter Knife. Do not modify!
package com.hikmah.dewi.fastprint.view;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.android.volley.toolbox.NetworkImageView;
import com.hikmah.dewi.fastprint.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DetailActivity_ViewBinding implements Unbinder {
  private DetailActivity target;

  private View view2131296302;

  @UiThread
  public DetailActivity_ViewBinding(DetailActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public DetailActivity_ViewBinding(final DetailActivity target, View source) {
    this.target = target;

    View view;
    target.coordinatorLayout = Utils.findRequiredViewAsType(source, R.id.coordinator_detail, "field 'coordinatorLayout'", CoordinatorLayout.class);
    target.nama_rental = Utils.findRequiredViewAsType(source, R.id.nama_rental, "field 'nama_rental'", TextView.class);
    target.alamat_rental = Utils.findRequiredViewAsType(source, R.id.alamat_rental, "field 'alamat_rental'", TextView.class);
    target.harga_hp = Utils.findRequiredViewAsType(source, R.id.harga_hp, "field 'harga_hp'", TextView.class);
    target.harga_warna = Utils.findRequiredViewAsType(source, R.id.harga_warna, "field 'harga_warna'", TextView.class);
    target.imageRP = Utils.findRequiredViewAsType(source, R.id.imagerp, "field 'imageRP'", NetworkImageView.class);
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
    DetailActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.coordinatorLayout = null;
    target.nama_rental = null;
    target.alamat_rental = null;
    target.harga_hp = null;
    target.harga_warna = null;
    target.imageRP = null;

    view2131296302.setOnClickListener(null);
    view2131296302 = null;
  }
}
