package com.cjf.ui.popup;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cjf.ui.popup.chip.OnSelectMultiLevelListener;
import com.cjf.util.R;
import com.cjf.util.utils.ResUtils;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.interfaces.OnCancelListener;
import com.lxj.xpopup.widget.CheckView;
import com.lxj.xpopup.widget.VerticalRecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>Title: CenterListMultiPopupView </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/10/23 16:54
 */
public class CenterListMultiplePopupView extends CenterPopupView implements View.OnClickListener {
    RecyclerView recyclerView;
    TextView tv_title;
    private TextView tv_cancel = null;
    private TextView tv_confirm = null;
    private CharSequence cancelText = null;
    private CharSequence confirmText = null;
    private OnCancelListener cancelListener = null;
    private OnSelectMultiLevelListener confirmListener = null;
    private BaseQuickAdapter<?, ? extends BaseViewHolder> mAdapter;

    public CenterListMultiplePopupView(@NonNull Context context, BaseQuickAdapter<?, ? extends BaseViewHolder> adapter) {
        this(context);
        mAdapter = adapter;
    }

    public CenterListMultiplePopupView(@NonNull Context context) {
        this(context, 0, 0);
    }

    /**
     * @param context
     * @param bindLayoutId     要求layoutId中必须有一个id为recyclerView的RecyclerView，如果你需要显示标题，则必须有一个id为tv_title的TextView
     * @param bindItemLayoutId 条目的布局id，要求布局中必须有id为iv_image的ImageView，和id为tv_text的TextView
     */
    public CenterListMultiplePopupView(@NonNull Context context, int bindLayoutId, int bindItemLayoutId) {
        super(context);
        this.bindLayoutId = bindLayoutId;
        this.bindItemLayoutId = bindItemLayoutId;
        addInnerContent();
    }

    @Override
    protected int getImplLayoutId() {
        return bindLayoutId == 0 ? R.layout._xpopup_center_impl_list_multi : bindLayoutId;
    }

    @Override
    protected void initPopupContent() {
        super.initPopupContent();
        tv_cancel = findViewById(R.id.tv_cancel);
        tv_confirm = findViewById(R.id.tv_confirm);
        tv_cancel.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);
        if (!TextUtils.isEmpty(cancelText)) {
            tv_cancel.setText(cancelText);
        }
        if (!TextUtils.isEmpty(confirmText)) {
            tv_confirm.setText(confirmText);
        }

        recyclerView = findViewById(R.id.recyclerView);
        if (recyclerView instanceof VerticalRecyclerView) {
            ((VerticalRecyclerView) recyclerView).setupDivider(popupInfo.isDarkTheme);
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        tv_title = findViewById(R.id.tv_title);

        if (tv_title != null) {
            if (TextUtils.isEmpty(title)) {
                tv_title.setVisibility(GONE);
                findViewById(R.id.xpopup_divider).setVisibility(GONE);
            } else {
                tv_title.setText(title);
            }
        }
        if (mAdapter == null) {
            mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(bindItemLayoutId == 0 ? R.layout._xpopup_adapter_text_match : bindItemLayoutId,
                    Arrays.asList(data)) {
                @Override
                protected void convert(@NotNull BaseViewHolder holder, String item) {
                    holder.setText(R.id.tv_text, item);
                    int position = holder.getAdapterPosition();
                    if (iconIds != null && iconIds.length > position) {
                        holder.getView(R.id.iv_image).setVisibility(VISIBLE);
                        holder.getView(R.id.iv_image).setBackgroundResource(iconIds[position]);
                    } else {
                        holder.getView(R.id.iv_image).setVisibility(GONE);
                    }
                    // 对勾View
                    CheckView checkView = holder.getView(R.id.check_view);
                    TextView textView = holder.<TextView>getView(R.id.tv_text);
                    textView.setTextColor(checkedPositionList.contains(position) ?
                            XPopup.getPrimaryColor() : getResources().getColor(R.color._xpopup_title_color));
                    if (!checkedPositionList.isEmpty()) {
                        checkView.setVisibility(checkedPositionList.contains(position) ? VISIBLE : GONE);
                        checkView.setColor(XPopup.getPrimaryColor());
                    } else {
                        checkView.setVisibility(GONE);
                        //如果没有选择，则文字居中
                        textView.setGravity(Gravity.CENTER);
                    }
                    if (bindItemLayoutId == 0 && popupInfo.isDarkTheme) {
                        textView.setTextColor(getResources().getColor(R.color._xpopup_white_color));
                    }
                }
            };

            mAdapter.setOnItemClickListener(new OnItemClickListener() {
                @SuppressWarnings("SuspiciousMethodCalls")
                @Override
                public void onItemClick(@NonNull BaseQuickAdapter<?, ?> baseAdapter, @NonNull View view, int position) {
                    if (position >= 0 && position < mAdapter.getItemCount()) {
                        String item = (String) mAdapter.getItem(position);
                        if (checkedPositionList.contains(position)) {
                            checkedPositionList.remove((Object) position);
                            checkedStringList.remove(item);
                        } else {
                            checkedPositionList.add(position);
                            checkedStringList.add(item);
                        }
                    }
                    mAdapter.notifyItemChanged(position);
//                if (popupInfo.autoDismiss) dismiss();
                }
            });
        }
        recyclerView.setAdapter(mAdapter);
        if (bindLayoutId == 0 && popupInfo.isDarkTheme) {
            applyDarkTheme();
        }
    }

    @Override
    protected void applyDarkTheme() {
        super.applyDarkTheme();
        tv_title.setTextColor(getResources().getColor(R.color._xpopup_white_color));
        tv_cancel.setTextColor(ResUtils.getColor(R.color._xpopup_white_color));
        tv_confirm.setTextColor(ResUtils.getColor(R.color._xpopup_white_color));
        ((ViewGroup) tv_title.getParent()).setBackgroundResource(R.drawable._xpopup_round3_dark_bg);
        findViewById(R.id.xpopup_divider).setBackgroundColor(getResources().getColor(R.color._xpopup_list_dark_divider));
    }

    CharSequence title;
    String[] data;
    int[] iconIds;

    public CenterListMultiplePopupView setStringData(CharSequence title, String[] data, int[] iconIds) {
        this.title = title;
        this.data = data;
        this.iconIds = iconIds;
        return this;
    }

    public CenterListMultiplePopupView setListener(
            OnSelectMultiLevelListener confirmListener, OnCancelListener cancelListener
    ) {
        this.cancelListener = cancelListener;
        this.confirmListener = confirmListener;
        return this;
    }

    public CenterListMultiplePopupView setCancelText(CharSequence cancelText) {
        this.cancelText = cancelText;
        return this;
    }

    public CenterListMultiplePopupView setConfirmText(CharSequence confirmText) {
        this.confirmText = confirmText;
        return this;
    }

    final List<Integer> checkedPositionList = new ArrayList<>();
    final List<String> checkedStringList = new ArrayList<>();

    /**
     * 设置默认选中的位置
     *
     * @param positionList
     * @return
     */
    public CenterListMultiplePopupView setCheckedPosition(List<Integer> positionList) {
        checkedPositionList.clear();
        checkedPositionList.addAll(positionList);
        return this;
    }

    @Override
    protected int getMaxWidth() {
        return popupInfo.maxWidth == 0 ? (int) (super.getMaxWidth() * .8f)
                : popupInfo.maxWidth;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == tv_cancel.getId()) {
            if (cancelListener != null) cancelListener.onCancel();
            dismiss();
        } else {
            if (confirmListener != null) {
                confirmListener.onSelect(this, checkedStringList, checkedPositionList, 0);
            }
        }
    }
}
