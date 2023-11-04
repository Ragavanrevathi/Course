package courses.dao.projection;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record TestDtoProjection(int id,String testName) {
}
