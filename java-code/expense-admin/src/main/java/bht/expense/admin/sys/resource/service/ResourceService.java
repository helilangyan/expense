package bht.expense.admin.sys.resource.service;

import bht.expense.admin.sys.resource.dao.ResourceMapper;
import bht.expense.admin.sys.resource.dto.ResourceTreeDto;
import bht.expense.admin.sys.resource.entity.ResourceEntity;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2020/7/14 10:47
 */

@Service
public class ResourceService {

    @Autowired
    ResourceMapper resourceMapper;


    @Transactional
    public ResourceEntity insert(ResourceEntity resourceEntity)
    {
        if(resourceEntity.getId()==null)
        {
            resourceMapper.insert(resourceEntity);
        }
        else {
            resourceMapper.updateById(resourceEntity);
        }
        return resourceEntity;
    }

    @Transactional
    public void del(String id)
    {
        resourceMapper.del(id);
    }

    @Transactional
    public void dels(String[] id)
    {
        resourceMapper.dels(id);
    }

    public ResourceEntity findById(String id)
    {
        return resourceMapper.selectById(id);
    }

    public PageInfo findByAll(Map<String,String> map , int page, int limit)
    {
        PageHelper.startPage(page, limit);
        List<ResourceEntity> list = resourceMapper.findByAll(map);
        PageInfo<ResourceEntity> pageInfo = new PageInfo<ResourceEntity>(list);
        return pageInfo;
    }

    public List<ResourceEntity> findByParentId(String parentId)
    {
        return resourceMapper.findByParentId(parentId);
    }

    public List<ResourceTreeDto> getResourceTree()
    {
        List<ResourceTreeDto> list = new ArrayList<>();

        List<ResourceEntity> rooleNodes = findByParentId("0"); //获取所有根节点
        if(rooleNodes!= null && rooleNodes.size()>0)
        {
            for(ResourceEntity resourceEntity : rooleNodes)
            {
                ResourceTreeDto resourceTreeDto = new ResourceTreeDto();
                BeanUtils.copyProperties(resourceEntity, resourceTreeDto);
                recursion(resourceEntity,resourceTreeDto);
                list.add(resourceTreeDto);
            }
        }

        return list;
    }

    public void  recursion(ResourceEntity resourceEntity , ResourceTreeDto resourceTreeDto)
    {
        List<ResourceEntity> childrenNodes = findByParentId(resourceEntity.getId());
        if(childrenNodes != null && childrenNodes.size()>0)
        {
            List<ResourceTreeDto> list = new ArrayList<>();

            for(ResourceEntity childrenNode : childrenNodes)
            {
                ResourceTreeDto childrenResourceTreeDto = new ResourceTreeDto();
                BeanUtils.copyProperties(childrenNode, childrenResourceTreeDto);
                list.add(childrenResourceTreeDto);

                recursion(childrenNode,childrenResourceTreeDto);
            }

            resourceTreeDto.setChildren(list);
        }
    }
}
