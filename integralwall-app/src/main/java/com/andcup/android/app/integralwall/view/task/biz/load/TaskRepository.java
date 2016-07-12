package com.andcup.android.app.integralwall.view.task.biz.load;

import com.andcup.lib.download.data.model.ResourceRepository;
import com.andcup.lib.download.service.repository.AbsRepositoryHandler;

/**
 * @author Jackoder
 * @version 2015/6/17
 */
public class TaskRepository extends AbsRepositoryHandler {

    @Override
    public ResourceCreator query(ResourceRepository repository, long taskId) {
        ResourceCreator creator = ResourceCreator.creator();
        return creator;
    }
}
