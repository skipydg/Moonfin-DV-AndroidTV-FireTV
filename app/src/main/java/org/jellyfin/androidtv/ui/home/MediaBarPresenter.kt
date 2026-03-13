package org.jellyfin.androidtv.ui.home

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.ui.platform.ComposeView
import androidx.leanback.widget.RowPresenter
import org.jellyfin.androidtv.ui.home.mediabar.MediaBarSlideshowView
import org.jellyfin.androidtv.ui.home.mediabar.MediaBarSlideshowViewModel
import org.jellyfin.androidtv.ui.navigation.Destinations
import org.jellyfin.androidtv.ui.navigation.NavigationRepository

class MediaBarPresenter(
	private val viewModel: MediaBarSlideshowViewModel,
	private val navigationRepository: NavigationRepository
) : RowPresenter() {
	
	override fun createRowViewHolder(parent: ViewGroup): RowPresenter.ViewHolder {
		val container = FrameLayout(parent.context).apply {
			layoutParams = ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT
			)
		}
		
		val composeView = ComposeView(parent.context).apply {
			layoutParams = FrameLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT
			).apply {
				bottomMargin = 40
			}
			
			setContent {
				MediaBarSlideshowView(
					viewModel = viewModel,
					onItemClick = { item ->
						navigationRepository.navigate(Destinations.itemDetails(item.itemId, item.serverId))
					}
				)
			}
		}
		
		container.addView(composeView)

		return ViewHolder(container)
	}
	
	override fun onBindRowViewHolder(vh: RowPresenter.ViewHolder, item: Any) = Unit
	
	override fun onUnbindRowViewHolder(vh: RowPresenter.ViewHolder) = Unit
	
	class ViewHolder(view: android.view.View) : RowPresenter.ViewHolder(view)
}
