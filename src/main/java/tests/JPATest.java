package tests;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import model.*;

public class JPATest {
	
	public static void main(String[] args) throws IOException{
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");
		EntityManager manager = factory.createEntityManager();
		
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		
		Cliente cliente = new Cliente();
		cliente.setNombres("Carlos");
		cliente.setApellidoPaterno("Campos");
		cliente.setApellidoMaterno("Miñano");
		
		Cuenta cuenta = new Cuenta();
		cuenta.setNumeroCuenta("123456789");
		cuenta.setSaldo(1000);
		cuenta.setActiva(true);
		
		TipoCuenta tipoCuenta = new TipoCuenta();
		tipoCuenta.setDescripcion("Cuenta de ahorros");
		tipoCuenta.setMoneda("PEN");
		
		Movimiento movimiento = new Movimiento();
		movimiento.setDescription("Deposito inicial");
		movimiento.setFecha(LocalDate.now());
		movimiento.setMonto(500d);
		
		
		Rol rolTitular = new Rol();
		rolTitular.setDescripcion("Titular");
		rolTitular.setActivo(true);
		
		Rol rolBeneficiario = new Rol();
		rolBeneficiario.setDescripcion("beneficiario");
		rolTitular.setActivo(true);
		
		
		//PERSISTIR ROLES
		manager.persist(rolTitular);
		manager.persist(rolBeneficiario);
		
		cuenta.setTipoCuenta(tipoCuenta);
		cuenta.getMovimientos().add(movimiento);
		
		cliente.getCuentas().add(cuenta);
		
		//AGREGAR ROLES
		cliente.getRoles().add(rolTitular);
		cliente.getRoles().add(rolBeneficiario);
		
		//INSERTAR CLIENTE
		manager.persist(cliente);		
		tx.commit();	
		
		
		 List<Cliente> lista = manager.createQuery("from Cliente", Cliente.class).getResultList();
	        for (Cliente c : lista) {
	            System.out.println("Cliente: " + c.getNombres() + " " + c.getApellidoPaterno() + " " + c.getApellidoMaterno());
	        }
		
		
	        manager.close();
	        factory.close();
			
		
		
	}

}
