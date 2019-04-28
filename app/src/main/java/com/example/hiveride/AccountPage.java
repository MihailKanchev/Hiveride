package com.example.hiveride;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.squareup.picasso.Picasso;


public class AccountPage extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.account_page, container, false);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
        Uri personPhoto = acct.getPhotoUrl();

        TextView name = (TextView) view.findViewById(R.id.profile_name);
        name.setText(acct.getDisplayName());

        ImageView image = (ImageView) view.findViewById(R.id.profile_picture);
        Picasso.get().load(personPhoto).resize(250,250).into(image);

        Button logout = (Button) view.findViewById(R.id.logout_button);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).signOut();
            }
        });

        return view;
    }
}
