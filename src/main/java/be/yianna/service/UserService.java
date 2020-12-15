package be.yianna.service;

import be.yianna.domain.User;
import be.yianna.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.FeatureDescriptor;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void patch(User toBePatched) {
        Optional<User> optionalUser = userRepository.findById(toBePatched.getIdUser());
        if (optionalUser.isPresent()) {
            User fromDb = optionalUser.get();
            String[] nullPropertyNames = getNullPropertyNames(toBePatched);
            BeanUtils.copyProperties(toBePatched,fromDb,nullPropertyNames);
            userRepository.save(fromDb);
        }
    }

    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        return Stream.of(wrappedSource.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
    }

    // then use Spring BeanUtils to copy and ignore null
    public static void myCopyProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }
}
