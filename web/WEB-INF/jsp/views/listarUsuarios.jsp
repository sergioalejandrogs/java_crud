<!--In order to manage all data from the DB and list it, we use dataTables to make it easier. See: 'https://datatables.net' -->
<%@taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8");%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/5.0.1/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.4/css/dataTables.bootstrap5.min.css">
        <script type="text/javascript" charset="utf8" src="https://code.jquery.com/jquery-3.5.1.js"></script>
        <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.11.4/js/jquery.dataTables.min.js"></script>
        <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.11.4/js/dataTables.bootstrap5.min.js"></script>
        <title>Listado de Usuarios</title>
        <script>
            $(document).ready(function(){
                $("#table").DataTable({
                    "info": true,
                    "paging": true,
                    "buttons": true,
                    "paginate": true,
                    "searching": true,
                    "language": {
                        url: "https://cdn.datatables.net/plug-ins/1.10.19/i18n/Spanish.json"
                    }
                });
            })
        </script>
    </head>
    <%@include file="header.jsp" %>
        <h1 style="text-align: center;">Listado de Usuarios</h1>
        <br>
        <div class="pane panel-primary" style="margin: auto; width: 60%;">
            <div>
                <a href="addUsuario.htm" class="btn btn-primary">Agregar Usuario</a>
            </div>
            <br>
            <table id="table" class="table table-hover">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Foto</th>
                        <th>Nombre</th>
                        <th>Apellido</th>
                        <th>Edad</th>
                        <th>Correo</th> 
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${usuario}" var="u">
                    <tr>
                        <td><c:out value="${u.id_usuario}"/></td>
                        <td>
                            <img style="height: 50px; width: 50px;" src="<c:url value="${u.foto}" />" />
                        </td>
                        <td><c:out value="${u.nombre}"/></td>
                        <td><c:out value="${u.apellido}"/></td>
                        <td><c:out value="${u.edad}"/></td>
                        <td><c:out value="${u.correo}"/></td>
                        <td>
                            <a href="updateUsuario.htm?id=${u.id_usuario}&foto=${u.foto}" class="btn btn-warning">Modificar</a>
                            <a href="deleteUsuario.htm?id=${u.id_usuario}&foto=${u.foto}" class="btn btn-danger">Eliminar</a>
                        </td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
            <br>
            <a href="index.htm" class="btn btn-secondary">Regresar </a>            
        </div>
    <%@include file="footer.jsp" %>
