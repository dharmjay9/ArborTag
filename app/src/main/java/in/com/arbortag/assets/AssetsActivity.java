package in.com.arbortag.assets;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import in.com.arbortag.BaseActivity;
import in.com.arbortag.BasePresenter;
import in.com.arbortag.R;

public class AssetsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assets);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void refresh() {

    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }
}
