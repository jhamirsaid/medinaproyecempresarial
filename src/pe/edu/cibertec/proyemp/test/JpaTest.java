package pe.edu.cibertec.proyemp.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import pe.edu.cibertec.proyemp.domain.Departamento;
import pe.edu.cibertec.proyemp.domain.Empleado;

public class JpaTest {
	
	
	private EntityManager manager;

	public JpaTest(EntityManager manager) {
		this.manager = manager;
	}
	
	public static void main(String[] args) {
		EntityManagerFactory factory =
				Persistence.createEntityManagerFactory("persistencemedina");
		EntityManager manager = factory.createEntityManager();
		
		
		JpaTest test = new JpaTest(manager);

		EntityTransaction tx = manager.getTransaction();
		
		tx.begin();
		
		//test.crearEmpleados();

		test.crearEmpleados2();
		
		tx.commit();
		test.listarEmpleados();

		System.out.println(".. done");
	}



	private void crearEmpleados2() {
		Departamento  departamento = new Departamento("Java");
		
		//esto tampoco List<Empleado> empleados = new ArrayList<Empleado>();
		Empleado bob =new Empleado("Bob");
		Empleado mike =new Empleado("Mike");
		
		//empleados.add(bob);
		//empleados.add(mike);
		//con eso te ahorras estas dos lineas de arriba
		departamento.setEmpleados(Arrays.asList(bob,mike));
		manager.persist(departamento);
		//como en este ejercicio ya se guarda el departamento
		bob.setDepartamento(departamento);
		mike.setDepartamento(departamento);
		
	}

	private void crearEmpleados() {
		int nroDeEmpleados =
				manager.createQuery("Select a From Empleado a",
						Empleado.class).getResultList().size();
		
		//asta aca veo todos los empleados y el size para la cantidad
		
		
		//la primera vez no va a encontrar y va a crear los empleados
		//como tiene un atributo departamento y lo guarda
		if (nroDeEmpleados == 0) {
			System.out.println("creando empleados");
			Departamento departamento = new Departamento("Java");
			manager.persist(departamento);
//aca creo estos dos empleados
			manager.persist(new Empleado("Bob",departamento));
			manager.persist(new Empleado("Mike",departamento));

		}
	}
	
	
	private void listarEmpleados() {
		List<Empleado> resultList =
				manager.createQuery("Select a From Empleado a", Empleado.class).getResultList();
		System.out.println("nro de empleados:" + resultList.size());
		for (Empleado next : resultList) {
			System.out.println("siguiente empleado: " + next);
		}

		
	}
	
	
	
	
}

