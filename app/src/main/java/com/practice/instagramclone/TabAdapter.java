package com.practice.instagramclone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabAdapter extends FragmentPagerAdapter {
    public TabAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HomeTab();
            case 1:
                return new UsersTab();
            case 2:
                return new SharePictureTab();
            case 3:
                return new FollowingTab();
            case 4:
                return new ProfileTab();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){

            case 0:
                return "Home";
            case 1:
                return "Users";
            case 2:
                return "Share";
            case 3:
                return "Following";
            case 4:
                return "Profile";
            default:
                return null;
        }
    }
}