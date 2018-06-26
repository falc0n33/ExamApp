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

import com.falc0n.android.examapp.algs.diffi_hellman.Pair;
import com.falc0n.android.examapp.algs.diffi_hellman.Person;
import com.falc0n.android.examapp.algs.el_gamal.ElGamal;


public class DiffiHellmanFragment extends Fragment {

    private EditText pEdit, qEdit, aEdit, bEdit;
    private Button mButton;
    private TextView mTextView;
    private int p, q, a, b;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_diffi_hellman, container, false);
        pEdit = v.findViewById(R.id.diffi_hellman_p);
        pEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        qEdit = v.findViewById(R.id.diffi_hellman_q);
        qEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        aEdit = v.findViewById(R.id.diffi_hellman_a);
        aEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        bEdit = v.findViewById(R.id.diffi_hellman_b);
        bEdit.setInputType(InputType.TYPE_CLASS_NUMBER);

        mButton = v.findViewById(R.id.diffi_hellman_button);
        mTextView = v.findViewById(R.id.diffi_hellman_textView);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pEdit.getText().length() > 0 &&
                        qEdit.getText().length() > 0 &&
                        aEdit.getText().length() > 0 &&
                        bEdit.getText().length() > 0) {
                    try {
                        p = Integer.parseInt(pEdit.getText().toString());
                        q = Integer.parseInt(qEdit.getText().toString());
                        a = Integer.parseInt(aEdit.getText().toString());
                        b = Integer.parseInt(bEdit.getText().toString());
                        String result = calculate(p, q, a, b);
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

    private String calculate(int p, int q, int a, int b) {
        StringBuilder sb = new StringBuilder();

        Person ap = new Person("A", p, q, a);
        Pair aPub = ap.getPublicKey();
        sb.append(aPub.getU() + "\n\n");

        Person bp = new Person("B", p, q, b);
        Pair bPub = bp.getPublicKey();
        sb.append(bPub.getU() + "\n\n");

        Pair aPriv = ap.getPrivateKey(Integer.parseInt(bPub.getV().toString()));
        sb.append(aPriv.getU() + "\n\n");

        Pair bPriv = bp.getPrivateKey(Integer.parseInt(aPub.getV().toString()));
        sb.append(bPriv.getU() + "\n\n");

        return sb.toString();
    }

}
