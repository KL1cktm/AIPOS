package by.yurhilevich.WebApp.service;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupService {
    boolean isGroupExists(String groupName);
    void saveGroup( String name );
    List<String> getAllGroups();
}
