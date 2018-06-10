package github.gulzar1996.besthackernewsapp.ui.detail.comment.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import github.gulzar1996.besthackernewsapp.ui.detail.comment.CommentFragment

@Module
internal abstract class CommentFragmentProvider {

    @ContributesAndroidInjector(modules = [CommentFragmentModule::class])
    internal abstract fun provideCommentragmentFactory(): CommentFragment
}