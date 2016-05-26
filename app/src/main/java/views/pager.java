package views;



import com.example.lenovo.myviews.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class pager extends Fragment {
	private String text;

	public void setText(String text) {
		this.text = text;
	}

	public pager() {
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		RelativeLayout rl = (RelativeLayout) inflater.inflate(
				R.layout.pagerfragment, container, false);
		TextView tv = (TextView) rl.findViewById(R.id.titleText);
		tv.setText(text);
		tv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), text,0).show();
				
			}
		});
		return rl;
	}
}
