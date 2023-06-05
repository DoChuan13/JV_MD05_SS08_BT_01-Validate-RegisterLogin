package backend.services.role;

import backend.model.Role;
import backend.model.enums.RoleName;
import backend.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceIMPL implements IRoleService {

    @Autowired
    IRoleRepository roleRepository;

    public RoleServiceIMPL() {
    }

    @Override
    public Iterable<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> findByRoleName(RoleName roleName) {
        Iterable<Role> roles = findAll();
        for (Role role : roles) {
            if (role.getRoleName() == roleName) {
                return roleRepository.findById(role.getId());
            }
        }
        return Optional.empty();
    }

    public void synchronizedRole() {
        Iterable<Role> roles = findAll();
        long roleSize = roles.spliterator().getExactSizeIfKnown();
        if (roleSize == 0) {
            List<Role> roleList = new ArrayList<>();
            roleList.add(new Role(1, RoleName.ADMIN));
            roleList.add(new Role(2, RoleName.PM));
            roleList.add(new Role(3, RoleName.USER));
            roleRepository.saveAll(roleList);
        }
        System.out.println(roleSize);
    }
}
