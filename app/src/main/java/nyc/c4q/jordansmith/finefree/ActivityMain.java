package nyc.c4q.jordansmith.finefree;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

public class ActivityMain extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navDrawerView;
    private ActionBarDrawerToggle drawerToggle;
    SubMenu submenu;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();
        setupDrawerContent(navDrawerView);
        drawerToggle = setupDrawerToggle();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_holder, new FragmentHome())
                .commit();



    }

    @Override
    protected void onResume() {
        super.onResume();
        addCarstoNav();
    }

    private void setupToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navDrawerView = (NavigationView) findViewById(R.id.nvView);
        drawerLayout.addDrawerListener(drawerToggle);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        Fragment fragment = null;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.home_fragment:
                fragmentClass = FragmentHome.class;
                break;
            case R.id.settings_fragment:
                fragmentClass = FragmentSettings.class;
                break;
            case R.id.add_car_fragment:
                fragmentClass = FragmentNewCar.class;
                break;
            default:
                fragmentClass = FragmentHome.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_holder, fragment).commit();

        menuItem.setChecked(true);

        setTitle(menuItem.getTitle());


        drawerLayout.closeDrawers();
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    public void addCarstoNav(){
        final Menu menu = navDrawerView.getMenu();
        if(submenu == null) {
            submenu = menu.addSubMenu("Your Cars");
            for (Car car : Car.getCarlist()) {
                submenu.add(car.getName()).setIcon(R.drawable.ic_car_black_36dp);
            }
        }
        else{
            for (Car car: Car.getCarlist()) {
                submenu.add(car.getName())
                        .setIcon(R.drawable.ic_car_black_36dp)
                        .setShowAsAction(MenuItem.);

        }


        }
    }




}
