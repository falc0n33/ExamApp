package com.falc0n.android.examapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.falc0n.android.examapp.algs.el_gamal.ElGamal;
import com.falc0n.android.examapp.algs.el_gamal.Pair;
import com.falc0n.android.examapp.algs.elliptic_curve.EllipticCurve;
import com.falc0n.android.examapp.algs.figure.Service;

import java.util.List;

public class ElGamalFragment extends Fragment {

    private EditText mEdit, pEdit, gEdit, xEdit, kEdit;
    private Button mButton;
    private TextView mTextView;
    private int m, p, g, x, k;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_el_gamal, container, false);
        mEdit = v.findViewById(R.id.el_message);
        mEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        pEdit = v.findViewById(R.id.el_p);
        pEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        gEdit = v.findViewById(R.id.el_g);
        gEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        xEdit = v.findViewById(R.id.el_x);
        xEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        kEdit = v.findViewById(R.id.el_k);
        kEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        mButton = v.findViewById(R.id.el_button);
        mTextView = v.findViewById(R.id.el_textView);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEdit.getText().length() > 0 &&
                        pEdit.getText().length() > 0 &&
                        gEdit.getText().length() > 0 &&
                        xEdit.getText().length() > 0 &&
                        kEdit.getText().length() > 0) {
                    try {
                        m = Integer.parseInt(mEdit.getText().toString());
                        p = Integer.parseInt(pEdit.getText().toString());
                        g = Integer.parseInt(gEdit.getText().toString());
                        x = Integer.parseInt(xEdit.getText().toString());
                        k = Integer.parseInt(kEdit.getText().toString());
                        ElGamal elGamal = new ElGamal(p, g, x, k);
                        String result = calculate(elGamal, m);
                        mTextView.setText(result);
                    } catch (Exception e) {
                        mTextView.setText(getString(R.string.error));
                    }
                } else {
                    mTextView.setText("");
                }

                try {
                    final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                } catch (Exception e) {
                }
            }
        });
        return v;
    }


    private String calculate(ElGamal elGamal, int m) {
        StringBuilder sb = new StringBuilder();

        sb.append(elGamal + "\n");

        Pair encrypted = elGamal.encrypt(m);
        sb.append(encrypted.getU() + "\n");

        long[] ab = (long[]) encrypted.getV();
        Pair decrypted = elGamal.decrypt(ab[0], ab[1]);
        sb.append(decrypted.getU() + "\n");

        Pair signature = elGamal.getSignature(m);
        sb.append(signature.getU() + "\n");

        Pair verify = elGamal.verifySignature(m, (long[]) signature.getV());
        sb.append(verify.getU() + "\n");

        return sb.toString();
    }
}
