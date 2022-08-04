<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome to Spring Web MVC CRUD project</title>
    </head>

    <%@include file="views/header.jsp" %>
        <h1 style="text-align: center">Index</h1>
        <br>
        <div class="container">
            <p>Hello! This is the default welcome page for a Spring Web MVC project.</p>
            <p><i>To display a different welcome page for this project, modify</i>
                <tt>index.jsp</tt> <i>, or create your own welcome page then change
                    the redirection in</i> <tt>redirect.jsp</tt> <i>to point to the new
                    welcome page and also update the welcome-file setting in</i>
                <tt>web.xml</tt>.</p>
            <a href="listarUsuarios.htm" class="btn btn-primary" style="margin-bottom: 5px">Ir al listado de usuarios</a>
            <br>
            <a href="formUsuario.htm" class="btn btn-secondary">Ir al formulario de validación de usuario</a> 
            <br><br>
            <a href="listarArticulos.htm" class="btn btn-primary" style="margin-bottom: 5px">Ir al listado de artículos</a> 
            <br>
            <a href="formArticulo.htm" class="btn btn-secondary">Ir al formulario de validación de artículo</a> 
            <br><br>
            <a href="listarVentas.htm" class="btn btn-primary">Ir al listado de ventas</a>
        </div>
        <%@include file="views/footer.jsp" %>
