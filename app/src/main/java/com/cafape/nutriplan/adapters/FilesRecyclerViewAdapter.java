package com.cafape.nutriplan.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cafape.nutriplan.R;
import com.cafape.nutriplan.database.entities.FileEntity;
import com.cafape.nutriplan.objects.SimpleAppointment;

import java.util.ArrayList;
import java.util.List;

public class FilesRecyclerViewAdapter extends RecyclerView.Adapter<FilesRecyclerViewAdapter.ViewHolder>
{
    private List<FileEntity> retrievedData;
    private List<FileEntity> retrievedData_copy; //needed for filtering
    private LayoutInflater layoutInflater;

    // data is passed into the constructor
    public FilesRecyclerViewAdapter(Context context, List<FileEntity> retrievedData) {
        this.layoutInflater = LayoutInflater.from(context);
        this.retrievedData = retrievedData;
        this.retrievedData_copy = new ArrayList<FileEntity>();
        this.retrievedData_copy.addAll(retrievedData);
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.row_file, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FileEntity dataGot = retrievedData.get(position);
        holder.rowFile_textView_fileName.setText(dataGot.getFilename());
        holder.rowFile_textView_fileName.setPaintFlags(holder.rowFile_textView_fileName.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        holder.rowFile_textView_fileName.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                fileopeningClickListener.onItemClick(dataGot);
            }
        });
        holder.rowFile_imageView_delete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                deleteFileClickListener.onItemClick(retrievedData.get(position));
            }
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return retrievedData.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView rowFile_textView_fileName;
        ImageView rowFile_imageView_delete;

        ViewHolder(View itemView) {
            super(itemView);
            rowFile_textView_fileName = itemView.findViewById(R.id.rowFile_textView_fileName);
            rowFile_imageView_delete = itemView.findViewById(R.id.rowFile_imageView_delete);
        }
    }

    // convenience method for getting data at click position
    FileEntity getItem(int id) {
        return retrievedData.get(id);
    }

    public void addToRetrievedData(FileEntity fileEntity) {
        retrievedData.clear();
        retrievedData.add(fileEntity);
        retrievedData_copy.add(fileEntity);
        retrievedData.addAll(retrievedData_copy);
    }

    //Declarative interface
    private FileopeningClickListener fileopeningClickListener;
    //set method
    public void setFileopeningClickListener(FileopeningClickListener fileopeningClickListener) {
        this.fileopeningClickListener = fileopeningClickListener;
    }
    //Defining interface
    public interface FileopeningClickListener
    {
        //Achieve the click method, passing the subscript.
        void onItemClick(FileEntity fileEntity);
    }

    private FilesRecyclerViewAdapter.DeleteFileClickListener deleteFileClickListener;
    public void setDeleteFileClickListener(FilesRecyclerViewAdapter.DeleteFileClickListener deleteFileClickListener) {
        this.deleteFileClickListener = deleteFileClickListener;
    }
    public interface DeleteFileClickListener {
        //Achieve the click method, passing the subscript.
        void onItemClick(FileEntity fileEntity);
    }
}
