package by.yurhilevich.WebApp.api;

import by.yurhilevich.WebApp.dto.GroupDTO;
import by.yurhilevich.WebApp.service.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api")
public class RestGroupController {

    @Autowired
    private GroupService groupService;

    @GetMapping("/get_groups")
    public ResponseEntity<List<String>> getGroups() {
        try {
            List<String> groups = groupService.getAllGroups();
            if (groups.isEmpty()) {
                return ResponseEntity.noContent().build(); // Возвращаем статус 204, если список пуст
            }
            return ResponseEntity.ok(groups); // Возвращаем список групп со статусом 200 OK
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // В случае ошибки возвращаем 500
        }
    }



    @PostMapping("/add_group")
    public ResponseEntity<Object> createGroup(@RequestBody GroupDTO groupDTO) {
        log.info("Received request to create group: {}", groupDTO.getName());
        System.out.println("Вызов корректный контроллера");
        if (groupService.isGroupExists(groupDTO.getName())) {
            log.info("Group already exists. Returning CONFLICT response.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Group already exists");
        }

        // Сохраняем группу в базе данных
        groupService.saveGroup(groupDTO.getName());
        log.info("Group '{}' created successfully", groupDTO.getName());

        return ResponseEntity.status(HttpStatus.CREATED).body("Created group successfully");
    }

}
