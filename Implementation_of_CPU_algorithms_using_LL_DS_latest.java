package OS;

import java.util.Scanner;

class node
{
	public String pid;
	public int at;
	public int bt;
	public int priority;
	public int extra;
	node next;
	public node(String pi, int a, int b) //for fcfs, sjf,rr
	{
		pid=pi;
		at=a;
		bt=b;
		extra=0;// can be used to store the remaining time in case of sjf
		priority=0;
		next=null;
		
	}
	
	public node(String pi, int a, int b,int ex,int pri) //for priority (non-preemptive)
	{
		pid=pi;
		at=a;
		bt=b;
		extra=ex;
		priority=pri; //to store priority value
		next=null;
		
	}
	
}

class linked_list
{
	node head=null;
	node tail=null;
	
	public void insert()	//insert back
	{
		Scanner s = new Scanner(System.in);
		System.out.print("Enter the process id :");
		String p1 = s.next();
		System.out.print("Enter the Arrival time :");
		int a1 = s.nextInt();
		System.out.print("Enter the Burst time :");
		int b1 = s.nextInt();
		System.out.print("Enter the priority :");
		int pr = s.nextInt();
		
		node temp = new node(p1,a1,b1,0,pr);
		
		if (head == null)
		{
			head = temp;
			tail=temp;
		}
		
		else
		{
			tail.next=temp;
			tail=temp;
			
		}
	}
	
	public void insert_front(node temp)	//insert front
	{		
		if (head == null)
		{
			head = temp;
			tail=temp;
		}
		
		else
		{
			temp.next=head;
			head=temp;
			
		}
	}
	
	public void insert_back(node temp)	//insert back
	{
		
		if (head == null)
		{
			head = temp;
			tail=temp;
		}
		
		else
		{
			tail.next=temp;
			tail=temp;
			
		}
	}
	
	public void delete_front()
	{
		if (head==null)
		{
			System.out.println("No element");
		}
		else if(head.next==null)
		{
			head=null;
		}
		else
		{
			head=head.next;
		}
	}
	
	public void order(String p1,int a,int b)
	{
		node temp=new node(p1,a,b);
		if(head==null)
		{
			head=temp;
			tail=temp;
		}
		else
		{
			if(temp.at<=head.at) 
				insert_front(temp);
			else
			{
				node t= head;
				node p=null;
				while(t!=null&& temp.at>t.at)
				{
					p=t;
					t=t.next;
				}
				
				if(t==null) 
					insert_back(temp);
				else
				{
					temp.next=p.next;
					p.next=temp;
				}
			}
		}
	}
	
	public void fcfs()
	{
		linked_list sort=new linked_list();
		linked_list sort_fcfs=new linked_list();
		
		node cur=head;
		while(cur != null)
		{
			sort.order(cur.pid,cur.at,cur.bt);
			cur=cur.next;
		}
				
		node c=sort.head;
		int prev_bt=0;
		int new_bt=0;
		int new_at=0;
		while(c!=null)
		{
			if(c==sort.head)
			{
				node t=new node(c.pid,c.at,c.bt);
				prev_bt=c.bt;
				sort_fcfs.insert_back(t);
			}
			else
			{
				new_bt=c.bt+prev_bt;
				new_at=prev_bt;
				node t1=new node(c.pid,new_at,new_bt);
				prev_bt=new_bt;
				new_at=new_bt;
				sort_fcfs.insert_back(t1);
			}
			c=c.next;
		}
		System.out.println("\nGantt Chart for First Come Fisrt Serve : \n");
		sort_fcfs.gantt_chat();
		display_final_table(sort_fcfs.head,1,0);
	}
	
	public void order_sjf(String p1,int a,int b,int extra)
	{
		node temp=new node(p1,a,b,extra,0);
		if(head==null)
		{
			head=temp;
			tail=temp;
		}
		else
		{
			if(temp.extra<=head.extra) 
				insert_front(temp);
			else
			{
				node t= head;
				node p=null;
				while(t!=null&& temp.extra>t.extra)
				{
					p=t;
					t=t.next;
				}
				
				if(t==null) 
					insert_back(temp);
				else
				{
					temp.next=p.next;
					p.next=temp;
				}
			}
		}
	}
	
	public void sjf()
	{		
		linked_list sort=new linked_list();
		linked_list sort_sjf=new linked_list();
		
		node hc=head;
		node cur=head.next;
		while(cur!=null)
		{
			cur.extra=cur.bt-cur.at;
			System.out.println(cur.extra);
			sort.order_sjf(cur.pid,cur.at,cur.bt,cur.extra);
			cur=cur.next;
		}
		
		sort.insert_front(hc);
				
		node c=sort.head;
		int prev_bt=0;
		int new_bt=0;
		int new_at=0;
		while(c!=null)
		{
			if(c==sort.head)
			{
				node t=new node(c.pid,c.at,c.bt);
				prev_bt=c.bt;
				sort_sjf.insert_back(t);
			}
			else
			{
				new_bt=c.bt+prev_bt;
				new_at=prev_bt;
				node t1=new node(c.pid,new_at,new_bt);
				prev_bt=new_bt;
				new_at=new_bt;
				sort_sjf.insert_back(t1);
			}
			c=c.next;			
		}
		System.out.println("\nGantt Chart for Shortest Job First : \n");
		sort_sjf.gantt_chat();
		display_final_table(sort_sjf.head,1,0);
	}
	
	public void order_priority_np(String p1,int a,int b,int prior)
	{
		node temp1=new node(p1,a,b,0,prior);
		if(head==null)
		{
			head=temp1;
			tail=temp1;
		}
		else
		{
			if(temp1.priority<=head.priority) 
				insert_front(temp1);
			else
			{
				node t= head;
				node p=null;
				while(t!=null&& temp1.priority>t.priority)
				{
					p=t;
					t=t.next;
				}
				
				if(t==null) 
					insert_back(temp1);
				else
				{
					temp1.next=p.next;
					p.next=temp1;
				}
			}
		}
	}
	
	public void priority_np()
	{		
		linked_list sort_p_np=new linked_list();
		linked_list sort_priority_np=new linked_list();
		
		node hc=head;
		node cur=head.next;
		while(cur!=null)
		{
			sort_p_np.order_priority_np(cur.pid,cur.at,cur.bt,cur.priority);
			cur=cur.next;
		}
		
		sort_p_np.insert_front(hc);
		
		
		node c=sort_p_np.head;
		int prev_bt=0;
		int new_bt=0;
		int new_at=0;
		while(c!=null)
		{
			System.out.println(c.pid);
			if(c==sort_p_np.head)
			{
				node t=new node(c.pid,c.at,c.bt);
				prev_bt=c.bt;
				sort_priority_np.insert_back(t);
			}
			else
			{
				new_bt=c.bt+prev_bt;
				new_at=prev_bt;
				node t1=new node(c.pid,new_at,new_bt,0,c.priority);
				prev_bt=new_bt;
				new_at=new_bt;
				sort_priority_np.insert_back(t1);
			}
			c=c.next;			
		}
		System.out.println("\nGantt Chart for Priority (Non Preemptive) : \n");
		sort_priority_np.gantt_chat();
		display_final_table(sort_priority_np.head,2,0);
	}
	
	public void rr(int tq)
	{
		linked_list rr_input=new linked_list();
		linked_list rr_input_copy=new linked_list();
		linked_list rr_output=new linked_list();
		linked_list sort_rr=new linked_list();
		linked_list final_rr=new linked_list();
		
		int count_var=0;
		node cur;
		cur=head;
		while(cur!=null)
		{
			rr_input.insert_back(cur);
			cur=cur.next;
		}
		
		node cur1;
		cur1=head;
		while(cur1!=null)
		{
			rr_input_copy.insert_back(cur1);
			cur1=cur1.next;
		}
		count_var=count(rr_input_copy.head);
		
		node ct=rr_input.head;
		int diff=0;
		int storage=0;
		
		if(ct.bt>tq)
		{
			diff=ct.bt-tq;
			storage=ct.bt-diff;
			
			node t=new node(ct.pid,ct.at,storage);
			
			rr_output.insert_back(t);
			node tt=new node(ct.pid,ct.at,diff);
			rr_input.insert_back(tt);
			ct=ct.next;
			rr_input.delete_front();
		}
		
		else
		{
			rr_output.insert_back(ct);
			ct=ct.next;
			rr_input.delete_front();
		}
		
		while (ct!=null)
		{
			if(ct.bt>3)
			{
				diff=ct.bt-tq;
				storage=ct.bt-diff;
				node t=new node(ct.pid,ct.at,storage);
				
				rr_output.insert_back(t);
				node tt=new node(ct.pid,ct.at,diff);
				rr_input.insert_back(tt);
				ct=ct.next;
				rr_input.delete_front();
			}
			
			else
			{
				rr_output.insert_back(ct);
				ct=ct.next;
				rr_input.delete_front();
			}
		}
				
		node c1=rr_output.head;
		int prev_bt=0;
		int new_bt=0;
		int new_at=0;
		while(c1!=null)
		{
			
			if(c1==sort_rr.head)
			{
				node t=new node(c1.pid,c1.at,c1.bt);
				prev_bt=c1.bt;
				sort_rr.insert_back(t);
			}
			else
			{
				new_bt=c1.bt+prev_bt;
				new_at=prev_bt;
				node t1=new node(c1.pid,new_at,new_bt,0,c1.priority);
				prev_bt=new_bt;
				new_at=new_bt;
				sort_rr.insert_back(t1);
			}
			c1=c1.next;			
		}
		
		System.out.println("\nGantt Chart for Round Robin : \n");
		sort_rr.gantt_chat();
		
		rr_input_copy.display();
		int b_value=0;
		node curv=rr_input_copy.head;
		node t=sort_rr.head;
		
		node temp1=null;
		int c_v=0;
		while(curv!=null && c_v!=count_var)
		{
			t=sort_rr.head;
			while(t!=null)
			{
				if(t.pid==curv.pid)
				{
					b_value=t.bt;
				}
				t=t.next;
			}
			temp1=new node(curv.pid,curv.at,b_value);
			//while(ft.pid!=temp1.pid)
			curv=curv.next;
			final_rr.insert_back(temp1);
			c_v=count(final_rr.head);
		}
		final_rr.display();
		display_final_table(final_rr.head,3,count_var);
	}
	
	public int count(node temp)
	{
		int count=0;
		while(temp!=null)
		{
			count++;
			temp=temp.next;
		}
		return count;
	}
	
	public void display()
	{	
		node curv;
		curv=head;
		System.out.printf("%-5s| %-10s | %-10s | %-10s | %-10s |\n\n"," ","Pid","Arrival time","Burst time","Priority");
		while(curv != null)
		{
			System.out.printf("%-5s| %-10s | %-12s | %-10s | %-10s |\n"," ",curv.pid,curv.at,curv.bt,curv.priority);
			curv=curv.next;
		}
		
	}
	
	public void display_final_table(node t,int ch, int val)
	{	
		node curv;// original table values
		curv=head;
		node c_copy;
		c_copy=head;
		
		node c; // this is for gantt chart table
		
		int tat=0;
		int wt=0;
		float t_tat=0;
		float t_wt=0;
		switch (ch)
		{
		case 1:
			System.out.println("\n\nThe table : \n");
			System.out.printf("%-3s| %-4s | %-10s | %-10s | %-10s | %-10s |\n\n"," ","Pid","Arrival time","Burst time","TurnAround time","Waiting time");
			while(curv != null)
			{
				c=t;
				while(c!=null)
				{
					if (curv.pid==c.pid)
					{
						break;
					}
					c=c.next;
				}
				tat=c.bt-curv.at;
				wt=tat-curv.bt;
				
				t_tat=t_tat+tat;
				t_wt=t_wt+wt;
				
				System.out.printf("%-3s| %-4s | %-12s | %-10s | %-15s | %-12s |\n"," ",curv.pid,curv.at,curv.bt,tat,wt);
				curv=curv.next;
			}
			int count = count(t);
			System.out.printf("%-37s | %-15s | %-12s |\n"," ",(t_tat/count),(t_wt/count));
			break;
			
		case 2:
			System.out.println("\n\nThe table : \n");
			System.out.printf("%-3s| %-4s | %-10s | %-10s | %-10s | %-10s | %-10s |\n\n"," ","Pid","Arrival time","Burst time","Priority","TurnAround time","Waiting time");
			while(curv != null)
			{
				c=t;
				while(c!=null)
				{
					if (curv.pid==c.pid)
					{
						break;
					}
					c=c.next;
				}
				tat=c.bt-curv.at;
				wt=tat-curv.bt;
				
				t_tat=t_tat+tat;
				t_wt=t_wt+wt;
				
				System.out.printf("%-3s| %-4s | %-12s | %-10s | %-10s | %-15s | %-12s |\n"," ",curv.pid,curv.at,curv.bt,curv.priority,tat,wt);
				curv=curv.next;
			}
			int count1 = count(t);
			System.out.printf("%-50s | %-15s | %-12s |\n"," ",(t_tat/count1),(t_wt/count1));
			break;
		/*	
		case 3:
			while(curv!=null)
			{
				System.out.println(curv.bt);
				curv=curv.next;
			}
			curv=head;
			System.out.println("\n\nThe table : \n");
			System.out.printf("%-3s| %-4s | %-10s | %-10s | %-10s | %-10s |\n\n"," ","Pid","Arrival time","Burst time","TurnAround time","Waiting time");
			while(curv != null)
			{
				c=t;
				while(c!=null)
				{
					if (curv.pid==c.pid)
					{
						break;
					}
					c=c.next;
				}
				tat=c.bt-curv.at;
				wt=tat-curv.bt;
				
				t_tat=t_tat+tat;
				t_wt=t_wt+wt;
				
				System.out.printf("%-3s| %-4s | %-12s | %-10s | %-15s | %-12s |\n"," ",curv.pid,curv.at,curv.bt,tat,wt);
				curv=curv.next;
			}
			int count3 = count(t);
			System.out.printf("%-37s | %-15s | %-12s |\n"," ",(t_tat/count3),(t_wt/count3));
			break;*/
			
		case 3:
			curv=t;
			node sam=head;
			System.out.println("\n\nThe table : \n");
			System.out.printf("%-3s| %-4s | %-10s | %-10s | %-10s | %-10s |\n\n"," ","Pid","Arrival time","Burst time","TurnAround time","Waiting time");
			int cc=count(head);
			while(curv != null && cc!=val && sam != null)
			{
				c=t;
				while(c!=null)
				{
					if (curv.pid==c.pid)
					{
						break;
					}
					c=c.next;
				}
				tat=c.bt-curv.at;
				wt=tat-c_copy.bt;
				
				t_tat=t_tat+tat;
				t_wt=t_wt+wt;
				
				System.out.printf("%-3s| %-4s | %-12s | %-10s | %-15s | %-12s |\n"," ",curv.pid,curv.at,c_copy.bt,tat,wt);
				curv=curv.next;
				sam=sam.next;
				c_copy=c_copy.next;
			}
			int count3 = count(t);
			System.out.printf("%-37s | %-15s | %-12s |\n"," ",(t_tat/count3),(t_wt/count3));
			break;
		}
	}
	
	public void gantt_chat()
	{
		node curv;
		curv=head;
		System.out.print(" | ");
		while(curv != null)
		{
			System.out.print(curv.pid + " | ");
			curv=curv.next;
		}
		
		System.out.println();
		node c=head;
		System.out.printf(" %-5s",c.at);
		while(c != null)
		{
			System.out.printf("%-5s",c.bt);
			c=c.next;
		}
		System.out.println("");
	}

}

public class Implementation_of_CPU_algorithms_using_LL_DS 
{

	public static void main(String[] args) 
	{
		linked_list l1=new linked_list();
				
		boolean c=true;
		System.out.println("\n\t\tCPU algorithm using Linked List Data Structure");
		while(c)
		{

			Scanner s = new Scanner(System.in);
			System.out.println("\n1. Insert"
					+ "\n2. Display"
					+ "\n3. First Come Fisrt Serve"
					+ "\n4. Shortest Job First"
					+ "\n5. Priority (Non-preemptive)"
					+ "\n6. Round Robin"
					+ "\n7. Exit"
					+ "\n\nEnter your choice : ");
			
			int ch =s.nextInt();
			switch (ch)
			{
				case 1 :
					l1.insert();
					break;
				
				case 2 :
					l1.display();
					break;
					
				case 3 :
					l1.fcfs();
					break;
					
				case 4 :
					l1.sjf();
					break;
								
				case 5 :
					l1.priority_np();
					break;
									
				case 6 :
					System.out.print("Enter the time quantum for Round Robin: ");
			        int q = s.nextInt();
					l1.rr(q);
					break;
					
				case 7:
					c=false;
					break;
					
				default :
					System.out.println("Invalid choice");
			}
		}
	}
}
