<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8");%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <title>Formulario de Artículos</title>
    </head>
    <%@include file="header.jsp" %>
        <h1 style="text-align: center">Formulario de Artículos</h1>
        <br>
        <div class="container">
            <form:form commandName="articulo" cssClass="container col-sm-6">
                <form:errors path="*" element="div" cssClass="alert alert-danger"/>
                <div class="mb-3">
                    <form:label path="titulo" cssClass="col-form-label">Título del artículo: </form:label>
                    <form:input path="titulo" cssClass="form-control"></form:input>
                </div>
                <div class="mb-3">
                    <form:label path="descripcion" cssClass="col-form-label">Descripción del artículo: </form:label>
                    <form:input path="descripcion" cssClass="form-control"></form:input>
                </div>
                <div class="mb-3">
                    <form:label path="precio" cssClass="col-form-label">Precio del artículo: </form:label>
                    <form:input path="precio" cssClass="form-control"></form:input>
                </div>
                <div class="mb-3">
                    <form:label path="foto" cssClass="col-form-label">Foto del artículo: </form:label>
                    <form:input path="foto" cssClass="form-control"></form:input>
                </div>
                <form:button name="enviar" Class="btn btn-primary">Enviar Datos</form:button> 
                <a href="listarArticulos.htm" class="btn btn-dark">Listar Artículos</a>
                <br>
                <br>
                <a href="index.htm" class="btn btn-secondary">Regresar</a>  
            </form:form>
        </div>                      
    <%@include file="footer.jsp" %>
