package com.ssnb.locdbdemo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.ssnb.locdb.DBOperatorManager;
import com.ssnb.locdbdemo.db.dbone.DBHelperOne;
import com.ssnb.locdbdemo.db.dbone.OneDBTabOneDB;
import com.ssnb.locdbdemo.db.dbone.OneDBTabTwoDB;
import com.ssnb.locdbdemo.db.dbtwo.DBHelperTwo;
import com.ssnb.locdbdemo.db.dbtwo.TwoDBTabOneDB;
import com.ssnb.locdbdemo.db.dbtwo.TwoDBTabTwoDB;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private Context context;
    private TextView hintView;
    private TextView createDBOneBtn;
    private TextView createDBTwoBtn;

    private CheckBox dbCheckBox;
    private CheckBox tableCheckBox;

    private TextView addDataBtn;
    private TextView deleteDataBtn;
    private TextView updateDataBtn;
    private TextView selectDataBtn;

    private boolean isCheckDBTwo = false;
    private boolean isCheckTableTwo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        initView();
        bindEvent();
    }

    private void initView() {
        hintView = findViewById(R.id.tv_hint_view);
        hintView.setMovementMethod(ScrollingMovementMethod.getInstance());

        createDBOneBtn = findViewById(R.id.tv_create_db_one_btn);
        createDBTwoBtn = findViewById(R.id.tv_create_db_two_btn);

        dbCheckBox = findViewById(R.id.cb_choose_db);
        tableCheckBox = findViewById(R.id.cb_choose_table);

        addDataBtn = findViewById(R.id.tv_add_data_btn);
        deleteDataBtn = findViewById(R.id.tv_delete_data_btn);
        updateDataBtn = findViewById(R.id.tv_update_data_btn);
        selectDataBtn = findViewById(R.id.tv_select_data_btn);
    }

    private void bindEvent() {
        createDBOneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBOperatorManager.singletion().getOperator(new DBHelperOne(context));
            }
        });

        createDBTwoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBOperatorManager.singletion().getOperator(new DBHelperTwo(context));
            }
        });

        dbCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isCheckDBTwo = isChecked;
            }
        });

        tableCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isCheckTableTwo = isChecked;
            }
        });

        addDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action(1);
            }
        });

        deleteDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action(2);
            }
        });

        updateDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action(3);

            }
        });

        selectDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action(4);
            }
        });

    }

    private void action(int action) {
        if (isCheckDBTwo) {
            if (isCheckTableTwo) {
                switch (action) {
                    case 1:
                        getTwoDBTabTwoDB().add("ssnb", getCurrentSecond());
                        break;
                    case 2:
                        getTwoDBTabTwoDB().delete();
                        break;
                    case 3:
                        getTwoDBTabTwoDB().update("mSnb",getCurrentSecond());
                        break;
                    case 4:
                        setHintView(getTwoDBTabTwoDB().select());
                        break;
                    default:
                        break;
                }
            } else {
                switch (action) {
                    case 1:
                        getTwoDBTabOneDB().add("ssnb", getCurrentSecond());
                        break;
                    case 2:
                        getTwoDBTabOneDB().delete();
                        break;
                    case 3:
                        getTwoDBTabOneDB().update("mSnb",getCurrentSecond());
                        break;
                    case 4:
                        setHintView(getTwoDBTabOneDB().select());
                        break;
                    default:
                        break;
                }
            }
        } else {
            if (isCheckTableTwo) {
                switch (action) {
                    case 1:
                        getOneDBTabTwoDB().add("ssnb", getCurrentSecond());
                        break;
                    case 2:
                        getOneDBTabTwoDB().delete();
                        break;
                    case 3:
                        getOneDBTabTwoDB().update("mSnb",getCurrentSecond());
                        break;
                    case 4:
                        setHintView(getOneDBTabTwoDB().select());
                        break;
                    default:
                        break;
                }
            } else {
                switch (action) {
                    case 1:
                        getOneDBTabOneDB().add("ssnb", getCurrentSecond());
                        break;
                    case 2:
                        getOneDBTabOneDB().delete();
                        break;
                    case 3:
                        getOneDBTabOneDB().update("mSnb",getCurrentSecond());
                        break;
                    case 4:
                        setHintView(getOneDBTabOneDB().select());
                        break;
                    default:
                        break;
                }
            }
        }
    }


    public OneDBTabOneDB getOneDBTabOneDB() {
        if (oneDBTabOneDB == null) {
            oneDBTabOneDB = new OneDBTabOneDB(context);
        }
        return oneDBTabOneDB;
    }

    public OneDBTabTwoDB getOneDBTabTwoDB() {
        if (oneDBTabTwoDB == null) {
            oneDBTabTwoDB = new OneDBTabTwoDB(context);
        }
        return oneDBTabTwoDB;
    }

    public TwoDBTabOneDB getTwoDBTabOneDB() {
        if (twoDBTabOneDB == null) {
            twoDBTabOneDB = new TwoDBTabOneDB(context);
        }
        return twoDBTabOneDB;
    }

    public TwoDBTabTwoDB getTwoDBTabTwoDB() {
        if (twoDBTabTwoDB == null) {
            twoDBTabTwoDB = new TwoDBTabTwoDB(context);
        }
        return twoDBTabTwoDB;
    }


    private OneDBTabOneDB oneDBTabOneDB;
    private OneDBTabTwoDB oneDBTabTwoDB;
    private TwoDBTabOneDB twoDBTabOneDB;
    private TwoDBTabTwoDB twoDBTabTwoDB;

    private int getCurrentSecond() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    private void setHintView(String msg){
        hintView.setText(msg);
    }


}
