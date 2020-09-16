package com.cjf.base.activity

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.cjf.util.R

/**
 * <p>Title: RecyclerActivity </p>
 * <p>Description: 列表 </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2020/9/3 8:47
 * @version : 1.0
 */
abstract class SwipeRecyclerListActivity<ADAPTER : RecyclerView.Adapter<VH>, VH : RecyclerView.ViewHolder> :
        AppCompatActivity(),
        SwipeRefreshLayout.OnRefreshListener {

    protected open var mSwipeRefreshLayout: SwipeRefreshLayout? = null
    protected open var mMaterialToolbar: Toolbar? = null

    /**
     * This field should be made private, so it is hidden from the SDK.
     * {@hide}
     */
    protected open var mAdapter: ADAPTER? = null
    protected open var mLayoutManager: RecyclerView.LayoutManager? = null

    /**
     * This field should be made private, so it is hidden from the SDK.
     * {@hide}
     */
    protected open var mList: RecyclerView? = null

    private val mHandler = Handler()
    private var mFinishedStart = false

    private val mRequestFocus = Runnable { mList!!.focusableViewAvailable(mList) }

    /**
     * Ensures the list view has been created before Activity restores all
     * of the view states.
     *
     * @see Activity.onRestoreInstanceState
     */
    override fun onRestoreInstanceState(state: Bundle) {
        ensureList()
        super.onRestoreInstanceState(state)
    }

    /**
     * @see Activity.onDestroy
     */
    override fun onDestroy() {
        mHandler.removeCallbacks(mRequestFocus)
        super.onDestroy()
    }

    /**
     * Updates the screen state (current list and other views) when the
     * content changes.
     *
     * @see Activity.onContentChanged
     */
    override fun onContentChanged() {
        super.onContentChanged()
        mMaterialToolbar = findViewById<Toolbar>(R.id.toolbar) as Toolbar
        mSwipeRefreshLayout =
                findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout) as SwipeRefreshLayout
        mList = findViewById<RecyclerView>(R.id.recyclerView) as RecyclerView
        if (mList == null) {
            throw RuntimeException(
                    "Your content must have a RecyclerView whose id attribute is " +
                            "'R.id.recyclerView'"
            )
        }
        if (mSwipeRefreshLayout == null) {
            throw RuntimeException(
                    "Your content must have a RecyclerView whose id attribute is " +
                            "'R.id.swipeRefreshLayout'"
            )
        }
        if (mMaterialToolbar == null) {
            throw RuntimeException(
                    "Your content must have a RecyclerView whose id attribute is " +
                            "'R.id.materialToolbar'"
            )
        }
        mMaterialToolbar?.setNavigationOnClickListener {
            backPage()
        }
        mSwipeRefreshLayout?.setColorSchemeColors(Color.RED, Color.GREEN, Color.YELLOW)
        mSwipeRefreshLayout?.setOnRefreshListener(this)
        if (mFinishedStart) {
            setListAdapter(mLayoutManager, mAdapter)
        }
        mHandler.post(mRequestFocus)
        mFinishedStart = true
    }

    protected open fun backPage() {
        finish()
    }

    /**
     * Provide the cursor for the list view.
     */
    protected open fun setListAdapter(
            layoutManager: RecyclerView.LayoutManager?, adapter: ADAPTER?
    ) {
        synchronized(this) {
            ensureList()
            mAdapter = adapter
            mLayoutManager = layoutManager
            mList?.let {
                it.layoutManager = layoutManager
                it.adapter = adapter
            }
        }
    }

    /**
     * Get the activity's list view widget.
     */
    protected open fun getToolbar(): Toolbar? {
        ensureList()
        return mMaterialToolbar
    }

    /**
     * Get the activity's list view widget.
     */
    protected open fun getSwipeRefreshLayout(): SwipeRefreshLayout? {
        ensureList()
        return mSwipeRefreshLayout
    }

    /**
     * Get the activity's list view widget.
     */
    protected open fun getListView(): RecyclerView? {
        ensureList()
        return mList
    }

    /**
     * Get the ListAdapter associated with this activity's ListView.
     */
    protected open fun getListAdapter(): ADAPTER? {
        return mAdapter
    }

    protected open fun ensureList() {
        if (mList != null) {
            return
        }
        setContentView(R.layout.simple_swipe_recycler_view_tool_bar)
    }

}