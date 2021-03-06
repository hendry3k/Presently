package journal.gratitude.com.gratitudejournal.ui.timeline

import android.app.Activity
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates3.AdapterDelegatesManager
import journal.gratitude.com.gratitudejournal.model.TimelineItem
import journal.gratitude.com.gratitudejournal.ui.bindingadapter.BindableAdapter
import org.threeten.bp.LocalDate

class TimelineAdapter(
    activity: Activity,
    showDayOfWeek: Boolean,
    linesPerEntry: Int,
    onClickListener: OnClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), BindableAdapter<List<TimelineItem>> {

    private lateinit var entries: List<TimelineItem>

    private val delegatesManager = AdapterDelegatesManager<List<TimelineItem>>()

    init {
        delegatesManager.addDelegate(TimelineEntryAdapterDelegate(activity, showDayOfWeek, linesPerEntry, onClickListener))
        delegatesManager.addDelegate(TimelineMilstoneAdapterDelegate(activity))
    }

    override fun setData(data: List<TimelineItem>) {
        entries = data

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegatesManager.onCreateViewHolder(viewGroup, viewType)
    }

    override fun getItemCount(): Int {
        return entries.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        return delegatesManager.onBindViewHolder(entries, position, holder)
    }

    override fun getItemViewType(position: Int): Int {
        return delegatesManager.getItemViewType(entries, position)
    }

    interface OnClickListener {
        fun onClick(clickedDate: LocalDate, isNewEntry: Boolean, numEntries: Int)
    }
}