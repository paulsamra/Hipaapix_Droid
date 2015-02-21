package swipe.android.hipaapix;


import swipe.android.hipaapix.core.BaseFragment;
import swipe.android.hipaapix.core.HipaaPixTabsActivityContainer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class AppTabBFirstFragment extends BaseFragment {

    private Button mGotoButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view       =   inflater.inflate(R.layout.app_tab_b_first_screen, container, false);

        mGotoButton =   (Button) view.findViewById(R.id.id_next_tab_b_button);
        mGotoButton.setOnClickListener(listener);

        return view;
    }

    private OnClickListener listener        =   new View.OnClickListener(){
        @Override
        public void onClick(View v){
            /* Go to next fragment in navigation stack*/
            mActivity.pushFragments(HipaaPixTabsActivityContainer.TAB_B, new AppTabBSecondFragment(),true,true);
        }
    };
}
