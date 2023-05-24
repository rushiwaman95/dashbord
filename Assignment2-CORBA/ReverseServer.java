import ReverseModule.Reverse;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
class ReverseServer
{
 public static void main(String[] args)
 {
 try
 {
 // initialize the ORB
 org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init(args,null);
 // initialize the portable object adaptor (BOA/POA) connects client request using object reference
 //uses orb method as resolve_initial_references
 POA rootPOA = 
POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
 rootPOA.the_POAManager().activate();
 // creating an object of ReverseImpl class
 
 ReverseImpl rvr = new ReverseImpl();
 //server consist of 2 classes ,servent and server. The servent is the subclass of ReversePOA which is generated by the idlj compiler

 // The servent ReverseImpl is the implementation of the ReverseModule  idl interface
 // get the object reference from the servant class
 //use root POA class and its method servant_to_reference
 org.omg.CORBA.Object ref = rootPOA.servant_to_reference(rvr);
 // System.out.println("Step1");
 Reverse h_ref = ReverseModule.ReverseHelper.narrow(ref);
 // Helper class provides narrow method that cast corba object reference (ref) into the java interface
 // System.out.println("Step2");
 // orb layer uses resolve_initial_references method to take initial  reference as NameService
 org.omg.CORBA.Object objRef = 
orb.resolve_initial_references("NameService");
 //Register new object in the naming context under the Reverse
 // System.out.println("Step3");
 NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
 //System.out.println("Step4");
 String name = "Reverse";
 NameComponent path[] = ncRef.to_name(name);
 ncRef.rebind(path,h_ref);
 //Server run and waits for invocations of the new object from the client
 System.out.println("Reverse Server reading and waiting....");
 orb.run();
 }
 catch(Exception e)
 {
 e.printStackTrace();
 }
 }
}