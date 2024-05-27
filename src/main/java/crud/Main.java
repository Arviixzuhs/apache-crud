package crud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        try {
            MySQLConnection mySQLConnection = new MySQLConnection();
            mySQLConnection.createConnection();
        } catch (SQLException e) {
            System.out.println("Ocurrio un error: " + e);
        }
    }
}

/* Creacion de la conexion al servidor local de MySQL */
class MySQLConnection {
    Connection createConnection() throws SQLException {
        String server = "jdbc:mysql://localhost:3306/parcialdos?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrival=false";
        String user = "root";
        String password = "arviixzuh";

        try (Connection connection = DriverManager.getConnection(server, user, password);) {
            System.out.println("Conectado correctamente a MySQL");
            Materia materia = new Materia();
            materia.main(connection);
        } catch (Exception e) {
            System.out.println("Ocurrio un error al conectar a MySQL: " + e);
            System.exit(0);
        }
        return null;
    }
}

class Materia {
    void main(Connection connection) {
        try {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                this.mensajeInicial();
                int option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1:
                        this.insertarMateria(connection);
                        break;
                    case 2:
                        this.listarMaterias(connection);
                        break;
                    case 3:
                        this.borrarMaterias(connection);
                        break;
                    case 4:
                        this.actualizarMateria(connection);
                        break;
                    case 5:
                        this.buscarMateria(connection);
                        break;
                    case 6:
                        System.exit(0);
                    default:
                        System.out.println("Opción inválida.");
                }
            }
        } catch (Exception e) {
            System.out.println("Ocurrio un error: " + e);
        }

    }

    void insertarMateria(Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        /* Query para insertar datos en la tabla "materia" */
        String Query = "INSERT INTO materia (nombreMateria, unidadesValorativas, descripcionMateria, estadoMateria) VALUES (?, ?, ?, ?)";

        /* Pedimos los datos al usuario */
        System.out.println("Ingresa el nombre: ");
        String nombreMateria = scanner.nextLine();

        System.out.println("Ingresa las unidades valorativas: ");
        String unidadesValorativas = scanner.nextLine();

        System.out.println("Ingresa la descripcion de la materia: ");
        String descripcionMateria = scanner.nextLine();

        System.out.println("Ingresa el estado de la materia: ");
        String estadoMateria = scanner.nextLine();

        PreparedStatement statement = connection.prepareStatement(Query);

        /*
         * Establecemos los datos a guardar en el Objeto
         */
        statement.setString(1, nombreMateria);
        statement.setString(2, unidadesValorativas);
        statement.setString(3, descripcionMateria);
        statement.setString(4, estadoMateria);

        /* Actualizamos la ejecucion para guardar */
        int rows = statement.executeUpdate();

        /* Verificamos si se guardo correctamente */
        if (rows > 0) {
            System.out.println("Materia guardada correctamente");
        }

    }

    void listarMaterias(Connection connection) throws SQLException {
        String Query = "SELECT * FROM materia";
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(Query);

        if (result.next()) {
            do {
                int idMateria = result.getInt("idMateria");
                String nombreMateria = result.getString("nombreMateria");
                String unidadesValorativas = result.getString("unidadesValorativas");
                String descripcionMateria = result.getString("descripcionMateria");
                String estadoMateria = result.getString("estadoMateria");

                System.out.println("id: " + idMateria);
                System.out.println("nombreMateria: " + nombreMateria);
                System.out.println("unidadesValorativas: " + unidadesValorativas);
                System.out.println("descripcionMateria: " + descripcionMateria);
                System.out.println("estadoMateria: " + estadoMateria);
                System.out.println("\n");
            } while (result.next());
        } else {
            System.out.println("No se encontraron materias.");
        }
    }

    void borrarMaterias(Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        String Query = "DELETE FROM materia WHERE idMateria = ?";

        System.out.println("Ingresa el id de la materia a borrar: ");
        int idMateria = scanner.nextInt();

        PreparedStatement statement = connection.prepareStatement(Query);
        statement.setInt(1, idMateria);

        int rows = statement.executeUpdate();

        if (rows > 0) {
            System.out.println("Materia borrada correctamente");
        } else {
            System.out.println("Materia no encontrada");
        }

    }

    void actualizarMateria(Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        String UpdateQuery = "UPDATE materia SET nombreMateria = ?, unidadesValorativas = ?, descripcionMateria = ? estadoMateria = ? WHERE idMateria = ?";
        PreparedStatement statement = connection.prepareStatement(UpdateQuery);

        System.out.println("Ingresa el id de la materia a actualizar: ");
        int idMateria = scanner.nextInt();

        System.out.println("Ingresa el nuevo nombre: ");
        String nombreMateria = scanner.nextLine();

        System.out.println("Ingresa las nuevas unidades valorativas: ");
        String unidadesValorativas = scanner.nextLine();

        System.out.println("Ingresa la nueva descripcion de la materia: ");
        String descripcionMateria = scanner.nextLine();

        System.out.println("Ingresa el nuevo estado de la materia: ");
        String estadoMateria = scanner.nextLine();

        statement.setString(1, nombreMateria);
        statement.setString(2, unidadesValorativas);
        statement.setString(3, descripcionMateria);
        statement.setString(4, estadoMateria);
        statement.setInt(5, idMateria);
        int rows = statement.executeUpdate();

        if (rows > 0) {
            System.out.println("Materia actualizada correctamente.");
        } else {
            System.out.println("Materia no encontrada");
        }

    }

    void buscarMateria(Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        String Query = "SELECT * FROM materia WHERE idMateria = ?";
        PreparedStatement statement = connection.prepareStatement(Query);

        /* Pedimos los datos al usuario */
        System.out.println("Ingresa el id de la materia a buscar: ");
        int idMateriaBuscar = scanner.nextInt();

        statement.setInt(1, idMateriaBuscar);
        ResultSet result = statement.executeQuery();

        if (result.next()) {
            int idMateria = result.getInt("idMateria");
            String nombreMateria = result.getString("nombreMateria");
            String unidadesValorativas = result.getString("unidadesValorativas");
            String descripcionMateria = result.getString("descripcionMateria");
            String estadoMateria = result.getString("estadoMateria");

            System.out.println("id: " + idMateria);
            System.out.println("nombreMateria: " + nombreMateria);
            System.out.println("unidadesValorativas: " + unidadesValorativas);
            System.out.println("descripcionMateria: " + descripcionMateria);
            System.out.println("estadoMateria: " + estadoMateria);
            System.out.println("\n");
        } else {
            System.out.println("Materia no encontrada");
        }

    }

    void mensajeInicial() {
        System.out.println("\nMENU");
        System.out.println("------------------");
        System.out.println("1. Agregar materia");
        System.out.println("2. Listar materias");
        System.out.println("3. Eliminar materia");
        System.out.println("4. Editar materia");
        System.out.println("5. Buscar materia");
        System.out.println("6. Salir");
        System.out.println("------------------");
        System.out.print("Ingrese su opción: ");
    }

}
