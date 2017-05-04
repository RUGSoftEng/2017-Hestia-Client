
package hestia.UI;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.rugged.application.hestia.R;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;

import java.util.ArrayList;
import java.util.List;

import hestia.backend.BackendInteractor;

/**
 * This abstract class is used as an abstract wrapper around the device list activity class.
 * @see DeviceListActivity
 */
public abstract class SingleFragmentActivity extends AppCompatActivity implements
        OnMenuItemClickListener {
    protected abstract Fragment createFragment();

    private static String TAG = "SingleFragmentActivity";
    private ContextMenuDialogFragment mMenuDialogFragment;
    private FragmentManager fm;
    private List<MenuObject> menuObjects;
    private BackendInteractor backendInteractor;

    private final int IP = 1;
    private final int LOGOUT = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        backendInteractor =  ((HestiaApplication)this.getApplication()).getBackendInteractor();
        if(backendInteractor.getIp()==null){
            showIpDialog();
        }



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
        menuObjects = getMenuObjects();
        initMenuFragment();

        mMenuDialogFragment.setItemClickListener(this);
    }

    private void initMenuFragment() {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(menuObjects);
        menuParams.setClosableOutside(true);
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
    }

    private List<MenuObject> getMenuObjects() {
        List<MenuObject> objects = new ArrayList<>();
        MenuObject close = new MenuObject();
        close.setResource(R.drawable.ic_action);
        objects.add(close);

        MenuObject ip = new MenuObject("Set Ip");
        ip.setResource(R.drawable.ic_router_black_24dp);
        objects.add(ip);

        MenuObject logout = new MenuObject("Logout");
        logout.setResource(R.drawable.ic_exit_to_app_black_24dp);
        objects.add(logout);

        return objects;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_menu:
                if (fm.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
                    mMenuDialogFragment.show(fm, "ContextMenuDialogFragment");
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onMenuItemClick(View clickedView, int position) {
        switch (position) {
            case IP:
                showIpDialog();
                break;
            case LOGOUT:
                gotoLoginActivity();
                break;
        }
    }

    private void gotoLoginActivity() {
        Intent i = new Intent(SingleFragmentActivity.this, LoginActivity.class);
        String s = "logout";
        i.putExtra("login", s);
        startActivity(i);
        finish();
    }

    private void showIpDialog() {
        IpDialog ipDialog = new IpDialog(SingleFragmentActivity.this);
        ipDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        ipDialog.show();
    }
}
