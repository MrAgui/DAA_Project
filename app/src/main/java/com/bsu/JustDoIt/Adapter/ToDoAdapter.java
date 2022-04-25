package com.bsu.JustDoIt.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.recyclerview.widget.RecyclerView;

import com.bsu.JustDoIt.AddNewTask;
import com.bsu.JustDoIt.MainActivity;
import com.bsu.JustDoIt.Model.ToDoModel;
import com.bsu.JustDoIt.R;
import com.bsu.JustDoIt.Utils.DatabaseHandler;

import java.util.List;
// For the functionalities of the application
//we have add, edit, delete.
public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {

    // For the list of ToDolist in the mainActivity
    private List<ToDoModel> toDoList;
    private MainActivity activity;
    private DatabaseHandler db;

    public ToDoAdapter(DatabaseHandler db, MainActivity activity){
        this.db = db;
        this.activity = activity;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(ViewHolder holder, int position){
        db.openDatabase();
        ToDoModel item = toDoList.get(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(toBoolean(item.getStatus()));
        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            //
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    db.updateStatus(item.getId(), 1);
                }else{
                    db.updateStatus(item.getId(), 0);
                }
            }
        });
    }

    // Not needed only required by the class
    public int getItemCount(){
        System.out.println("total list in App " + toDoList.size());
        return toDoList.size();
    }


    // For the Status int convert to Boolean Value
    private boolean toBoolean(int n){
        return n!=0; //if n is not = 0 return true
    }

    //Constructor for toDoList
    public void setTask(List<ToDoModel> toDoList ){
        this.toDoList = toDoList;
        //to update the recycler view real time
        notifyDataSetChanged();
    }

    public Context getContext(){
        return activity;
    }

    public void deleteItem(int position){
        ToDoModel item = toDoList.get(position);
        db.deleteTask(item.getId());
        toDoList.remove(position);
        notifyItemRemoved(position); //to update the recycler view real time
    }



    // For editing the tasks
    public void editItem(int position) {
        //get the item position you want to update
        ToDoModel item = toDoList.get(position);
        Bundle bundle = new Bundle();
        // get the key value and pass it to the functions
        bundle.putInt("id", item.getId());
        bundle.putString("task", item.getTask());
        AddNewTask fragment = new AddNewTask();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(), AddNewTask.TAG);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox task;

        ViewHolder(View view){
            super(view);
            task = view.findViewById(R.id.toDoCheckBox);
        }
    }
}
