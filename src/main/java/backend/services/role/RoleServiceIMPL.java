package backend.services.role;

import backend.model.Role;
import backend.model.enums.RoleName;
import backend.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class RoleServiceIMPL implements IRoleService {
    private static final Set<Role> roleSet = new HashSet<>();

    static {
        roleSet.add(new Role((byte) 1, RoleName.ADMIN));
        roleSet.add(new Role((byte) 2, RoleName.PM));
        roleSet.add(new Role((byte) 3, RoleName.USER));
    }

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
}
