import boto3

def encrypt_ebs_volumes(volume_ids):
    # Initialize the EC2 client
    ec2_client = boto3.client('ec2')

    for volume_id in volume_ids:
        try:
            # Describe the volume to check its encryption status
            volume_info = ec2_client.describe_volumes(VolumeIds=[volume_id])['Volumes'][0]
            
            # Check if the volume is already encrypted
            if volume_info['Encrypted']:
                print(f"Volume {volume_id} is already encrypted.")
            else:
                # Encrypt the volume
                ec2_client.modify_volume(
                    VolumeId=volume_id,
                    EncryptionOptions={
                        'EnableEncryption': True
                    }
                )
                print(f"Volume {volume_id} has been encrypted.")
        except Exception as e:
            print(f"Error encrypting volume {volume_id}: {str(e)}")

if __name__ == '__main__':
    volume_ids = ['vol-00187a754d8bd4899']  # Replace with your EBS volume IDs
    encrypt_ebs_volumes(volume_ids)
