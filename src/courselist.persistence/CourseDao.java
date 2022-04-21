package courselist.persistence;

import courselist.Course;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * A data access object for Course entities.
 * Requires a database connection be supplied.
 *
 */
public class CourseDao {
    // a connection to the database
    private Connection connection;

    /**
     * Initialize a new CourseDao with a JDBC connection to the database.
     *
     * @param connection the JDBC connection
     */
    public CourseDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * Retrieve a course from the database given its id.
     *
     * @param id
     * @return Return a Course with the requested id.
     */
    public Course get(int id) {
        Course course = null;
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM courses WHERE id = " + id;
            ResultSet rs = statement.executeQuery(query);
            // create a Course object from the first row of the ResultSet
            if (rs.next()) {
                course = makeCourse(id, rs);
            }
        }
        catch (SQLException ex) {
            // rethrow the exception with a descriptive message
            throw new RuntimeException("Problem getting cause from database", ex);
        }

        return course;
    }

    private Course makeCourse(int id, ResultSet rs) throws SQLException {
        Course course;
        String courseNumber = rs.getString("course_number");
        String title = rs.getString("title");
        int credits = rs.getInt("credits");
        double difficulty = rs.getDouble("difficulty");
        course = new Course(courseNumber, title, credits);
        course.setDifficulty(difficulty);
        course.setId(id);
        return course;
    }
}