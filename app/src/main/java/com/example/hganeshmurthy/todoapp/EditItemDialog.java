package com.example.hganeshmurthy.todoapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

/**
 * A placeholder fragment containing a simple view.
 */
public class EditItemDialog extends DialogFragment implements OnEditorActionListener{
    EditText etEditItem;
    Spinner spPriority;
    private final int REQUEST_CODE = 20;
    public int RESULT_CODE_CANCEL=30;
    Button btnEditItem;
    String item,position,date,priority;


    long pos;
    public interface EditItemDialogListener {
        void onFinishEditDialog(String item,String priority,String position,String date);
    }

    public EditItemDialog() {
    }

    public static EditItemDialog newInstance(String item,Long position,String date, String priority) {
        EditItemDialog frag = new EditItemDialog();
        Bundle args = new Bundle();
        args.putString("item", item);
        args.putString("date", date);
        args.putString("priority", priority);
        args.putString("position", position + "");

        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_edit_fragment, container);
        etEditItem = (EditText) view.findViewById(R.id.etEditItem);
        etEditItem.setOnEditorActionListener(this);
        etEditItem.setRawInputType(InputType.TYPE_CLASS_TEXT);
        etEditItem.setImeOptions(EditorInfo.IME_ACTION_GO);
        spPriority = (Spinner) view.findViewById(R.id.spPriority);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


         item = getArguments().getString("item", "Enter Name");
         position = getArguments().getString("position", "Enter Name");
         date = getArguments().getString("date", "Enter Name");
         priority = getArguments().getString("priority", "Enter Name");

        etEditItem = (EditText) view.findViewById(R.id.etEditItem);
        etEditItem.setText(item);



        int selection=0;
        if (priority.equals("Medium"))
            selection = 1 ;
        else if (priority.equals("Low"))
            selection = 2 ;
        spPriority.setSelection(selection);

        spPriority.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

    }

    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_GO == actionId) {
            // Return input text to activity
            item = etEditItem.getText().toString();
            priority = String.valueOf(spPriority.getSelectedItem());
            EditItemDialogListener listener = (EditItemDialogListener) getActivity();
            listener.onFinishEditDialog(item,position,date,priority);
            dismiss();

            return true;
        }
        return false;
    }




}
