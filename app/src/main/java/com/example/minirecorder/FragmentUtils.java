package com.example.minirecorder;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.util.Log;

public class FragmentUtils {

    private static final String TAG = "FragmentUtils";

    public static final int MAIN_CONTAINER = R.id.main_fragment_layout;

    public static boolean addFragmentToMainContainer(Activity activity, SuperFragment fragment, String tagName, boolean backStack) { //common
        return FragmentUtils.addFragmentTo(activity, fragment, MAIN_CONTAINER, null, tagName, backStack);
    }

    public static boolean addFragmentToMainContainer(Activity activity, SuperFragment fragment, String state, String tagName, boolean backStack) {
        return FragmentUtils.addFragmentTo(activity, fragment, MAIN_CONTAINER, state, tagName, backStack);
    }

    public static boolean addFragmentTo(Activity activity, SuperFragment fragment, int container, String stateName, String tagName, boolean backStack) {
        boolean result = false;

        if (activity != null && fragment != null) {

            FragmentManager fragmentManager = activity.getFragmentManager();
            fragmentManager.addOnBackStackChangedListener(FragmentUtils.getListener(activity, container));

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(container, fragment, tagName);

            if(backStack)
                fragmentTransaction.addToBackStack(tagName);
            fragmentTransaction.commit();

            result = true;
        }

        return result;
    }

    public static boolean changeIfFragmentExist(Activity activity, String tag){
        Boolean result = false;
        FragmentManager fragmentManager = activity.getFragmentManager();
        SuperFragment inputFragment = (SuperFragment) fragmentManager.findFragmentByTag(tag);

        if (inputFragment == null){
            Log.d(TAG, "No Fragment is found, need create in MAIN");

        }
        else{
            Log.d(TAG, "Fragment is found");
            onBackTo(activity, tag);
            result = true;

        }

        return result;
    }

    public static void onBackTo(Activity activity, String name) {
        if (activity != null && activity.getFragmentManager() != null)
            Log.d(TAG, "onBackTo: "+name);
        activity.getFragmentManager().popBackStack(name, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public static void onBack(Activity activity) {
        if (activity != null && activity.getFragmentManager() != null)
            activity.getFragmentManager().popBackStack();
    }

    private static FragmentManager.OnBackStackChangedListener getListener(final Activity activity, final int container) {
        FragmentManager.OnBackStackChangedListener result = new FragmentManager.OnBackStackChangedListener() {
            public void onBackStackChanged() {
                FragmentManager manager = activity.getFragmentManager();

                if (manager != null) {
                    SuperFragment currFrag = (SuperFragment) manager.findFragmentById(container);
                    if(currFrag != null)currFrag.onFragmentResume();
                }
            }
        };

        return result;
    }
}
