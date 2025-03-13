package by.yurhilevich.WebApp.service;

import by.yurhilevich.WebApp.models.Group;
import by.yurhilevich.WebApp.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupService{
    @Autowired
    GroupRepository groupRepository;

    public boolean isGroupExists(String groupName) {
        return groupRepository.existsByName(groupName);
    }

    public void saveGroup( String name ){
        if (!isGroupExists( name)){
            Group group = new Group();
            group.setName( name );
            groupRepository.save( group );
        }
    }

    public List<String> getAllGroups() {
        List<Group> groups = groupRepository.findAll( );
        List<String> groupsName = groups.stream()
                .map(Group::getName)
                .collect(Collectors.toList());

        System.out.println(groupsName);
        return groupsName;
    }
}