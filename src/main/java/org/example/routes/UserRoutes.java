package org.example.routes;

import io.javalin.apibuilder.EndpointGroup;
import org.example.controller.UserController;
import org.example.security.RouteRoles;

import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

public class UserRoutes {
    private final UserController userController = new UserController();

    public EndpointGroup getRoutes() {
        return () -> {
            path("auth", () -> {
                post("/login", userController::login, RouteRoles.ANYONE);
                post("/register", userController::register, RouteRoles.ANYONE);
            });
        };
    }


}
