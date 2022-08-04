<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8");%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <title>Modificar Usuario</title>
    </head>
    <%@include file="header.jsp" %>
        <h1 style="text-align: center">Modificar Usuario</h1>
        <br>
        <div class="container">
            <form:form commandName="usuario" cssClass="container col-sm-6" enctype="multipart/form-data">
                <form:errors path="*" element="div" cssClass="alert alert-danger"/>
                <div class="mb-3">
                    <form:label path="nombre" cssClass="col-form-label">Nombre de usuario: </form:label>
                    <form:input path="nombre" cssClass="form-control"  required="required"></form:input>
                </div>
                <div class="mb-3">
                    <form:label path="apellido" cssClass="col-form-label">Apellido de usuario: </form:label>
                    <form:input path="apellido" cssClass="form-control"  required="required"></form:input>
                </div>
                <div class="mb-3">
                    <form:label path="edad" cssClass="col-form-label">Edad de usuario: </form:label>
                    <form:input path="edad" cssClass="form-control"  required="required"></form:input>
                </div>
                <div class="mb-3">
                    <form:label path="correo" cssClass="col-form-label">Correo de usuario: </form:label>
                    <form:input path="correo" cssClass="form-control"  required="required"></form:input>
                </div>
                <div class="mb-3">
                    
                    <form:label path="foto" cssClass="col-form-label">Foto de usuario: </form:label>
                    <img style="height: 50px; width: 50px;" src="<c:url value="${usuario.foto}" />" />
                    <form:input path="foto" cssClass="form-control" type="file"  required="required"></form:input>
                </div>
                <form:button name="enviar" Class="btn btn-primary">Enviar Datos</form:button> 
                <a href="listarUsuarios.htm" class="btn btn-secondary">Regresar</a>
            </form:form>
        </div>        
    <%@include file="footer.jsp" %>
