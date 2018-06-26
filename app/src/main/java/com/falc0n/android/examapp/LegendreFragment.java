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

import com.falc0n.android.examapp.algs.legendre.Legendre;

import org.w3c.dom.Text;

public class LegendreFragment extends Fragment {
    private EditText aEdit, bEdit;
    private Button mButton;
    private int a, b;
    private TextView mTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_legendre, container, false);
        aEdit = (EditText) v.findViewById(R.id.legendre_a);
        aEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        bEdit = (EditText) v.findViewById(R.id.legendre_b);
        bEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        mButton = (Button) v.findViewById(R.id.legendre_button);
        mTextView = (TextView) v.findViewById(R.id.legendre_textView);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (aEdit.getText().toString().length() > 0 && bEdit.getText().toString().length() > 0) {
                    try {
                        a = Integer.parseInt(aEdit.getText().toString());
                        b = Integer.parseInt(bEdit.getText().toString());
                        Legendre legendre = new Legendre(a, b);
                        String result = legendre.solve();
                        mTextView.setText(result);
                    } catch (Exception e) {
                        mTextView.setText(getString(R.string.error));
                    }

                    try {
                        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                    } catch (Exception e) {

                    }
                } else {
                    mTextView.setText("");
                }
            }
        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
