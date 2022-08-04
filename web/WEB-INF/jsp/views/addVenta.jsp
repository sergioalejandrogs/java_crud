<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <title>Registro de Venta</title>
    </head>
    <%@include file="header.jsp" %>
        <h1 style="text-align: center">Registro de Venta</h1>
        <br>
        <div class="container">
            <form:form commandName="venta" cssClass="container col-sm-6" method="post" >
                <form:errors path="*" element="div" cssClass="alert alert-danger"/>
                <fieldset disabled>
                    <div class="mb-3">
                        <form:label path="id_venta" cssClass="col-form-label">Código de venta: </form:label>
                        <form:input path="id_venta" cssClass="form-control" type="text"></form:input>
                    </div>
                </fieldset>
                <div class="mb-3">
                    <form:label path="id_usuario" cssClass="col-form-label">Usuario: </form:label>
                    <form:select path="id_usuario" cssClass="form-control">
                        <c:forEach var="user" items="${id_usuario}">
                            <option value="${user.id_usuario}">${user.nombre} ${user.apellido}</option>
                        </c:forEach>
                    </form:select>
                </div>
                <div class="mb-3">
                    <form:label path="fecha_venta" cssClass="col-form-label">Fecha de venta: </form:label>
                    <form:input path="fecha_venta" cssClass="form-control" type="date"  required="required"></form:input>
                </div>
                <div class="mb-3">
                    <form:label path="id_articulo" cssClass="col-form-label">Artículo: </form:label>
                    <form:select path="id_articulo" cssClass="form-control">
                        <c:forEach var="art" items="${id_articulo}">
                            <option value="${art.id_articulo}">${art.titulo}</option>
                        </c:forEach>                        
                    </form:select>
                </div>
                <form:button name="enviar" Class="btn btn-primary">Registrar Venta</form:button> 
                <a href="listarVentas.htm" class="btn btn-secondary">Regresar</a>  
            </form:form>
        </div>                      
    <%@include file="footer.jsp" %>
